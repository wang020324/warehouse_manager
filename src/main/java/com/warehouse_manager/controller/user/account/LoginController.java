package com.warehouse_manager.controller.user.account;

import com.google.code.kaptcha.Producer;
import com.warehouse_manager.pojo.user.auth.Auth;
import com.warehouse_manager.pojo.user.CurrentUser;
import com.warehouse_manager.pojo.user.LoginUser;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.pojo.user.User;
import com.warehouse_manager.service.user.account.UserService;
import com.warehouse_manager.service.user.auth.AuthService;
import com.warehouse_manager.utils.DigestUtil;
import com.warehouse_manager.utils.TokenUtils;
import com.warehouse_manager.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {
    //注入DefaultKaptcha对象的bean对象，来生成验证码
    @Resource(name = "captchaProducer")
    private Producer producer;

    //注入redis模板
    @Autowired
    private StringRedisTemplate redisTemplate;



    //服务器后台生成验证码图片，图片然后响应给前端，前段以image来显示
    @RequestMapping("/captcha/captchaImage")
    public void capchaImage(HttpServletResponse response) throws IOException {

        //将验证码图片写给前端
        ServletOutputStream out = null;

        try {
            String text =producer.createText();//生成验证码图片的文本
            //使用验证码文本生成验证码图片
            BufferedImage image=producer.createImage(text);//目前在内存里
            //将验证码文本作为键值保存到redis，以帮正验证,并设置验证码保存时间
            redisTemplate.opsForValue().set(text,"",60*30, TimeUnit.SECONDS);


        /*
        将验证码图片响应给前端

        * */
            //设置响应正文image/jpeg
            response.setContentType("image/jpeg");

            out = response.getOutputStream();
            ImageIO.write(image,"jpg",out);//使用响应对象的字节输出流写入验证码图片，自然是给前端写入
            //刷新
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭字节输出流
            if(out!=null)
            {
                try{
                    out.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }

            }
        }





    }

//登录的url接口 login
    /*
      参数@RequestBody LoginUser loginuser  表示接收并封装前端传递的登录的用户信息的json数据
      返回值Result对象---表示向前端响应json串，包含响应状态，成功失败响应，响应信息，响应数据
    * */

    @Autowired
    private UserService userService;
    //注入TokenUtils对象
    @Autowired
    private TokenUtils tokenUtils;
    //注入redis模板
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @RequestMapping("/login")
    public Result login(@RequestBody LoginUser loginUser)
    {
        //拿到客户录入的验证码
        String code=loginUser.getVerificationCode();
        if(!redisTemplate.hasKey(code)){
            //没有录入返回错误的方法对象
            return Result.err(Result.CODE_ERR_BUSINESS,"验证码错误！");
        }
        //先根据账号查询用户
      User user=userService.queryUserByCode(loginUser.getUserCode());
      if(user!=null)
      {
          //如果用户不为空，说明账号存在
          if(user.getUserState().equals(WarehouseConstants.USER_STATE_PASS)){
              //说明用户已经经过管理员做审核
              //首先先拿到用户录入的密码(接收到的是未加密的密码)
              String userPwd = loginUser.getUserPwd();
              //然后再进行md5加密
              userPwd=DigestUtil.hmacSign(userPwd);
              //加密后的密码等于数据库查询到的已经加密存储的密码
              if(userPwd.equals(user.getUserPwd())){
                  //密码合法
                  //生成jwt_token,并且存入redis
                  CurrentUser currentUser =new CurrentUser(user.getUserId(),user.getUserCode(),user.getUserName());
                  String token =tokenUtils.loginSign(currentUser,userPwd);
                  //向客户端响应jwt_token
                  return Result.ok("登陆成功",token);
              }else{
                  //密码错误
                  return Result.err(Result.CODE_ERR_BUSINESS,"密码错误！");
              }
          }else{
              //用户未审核
              return Result.err(Result.CODE_ERR_BUSINESS,"用户未审核！");
          }
      }else{
      //返回失败响应
          return Result.err(Result.CODE_ERR_BUSINESS,"账号不存在");
      }

    }

    //获取当前登录的用户信息的url接口/curr-user
    //拿到前端归还的token，使用token工具类解析的封装方法拿到从token中解析出来的用户信息并封装到CurrentUser中
   // @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token 表示将请求头token的值(前端归还的token赋值给请求处理方法入参变量token)
    @RequestMapping("/curr-user")
    public Result currentUser(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        //解析token，拿到封装了当前登录用户信息的CurrentUser对象
        CurrentUser currentUser =tokenUtils.getCurrentUser(token);
       //响应
        return Result.ok(currentUser);
    }

    //加载用户对应的权限下的权限菜单
    //后台只是从数据库中查询出该用户权限下的所有菜单，然后用户将菜单转换成菜单树，最后将菜单树响应给前端，前端只需要做循环迭代展示出菜单树即可
   //注入AuthService
    @Autowired
    private AuthService authService;

    @RequestMapping("/user/auth-list")
    public Result loadAuthTree(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token)
    {
            //拿到当前登录用户的id
       CurrentUser currentUser= tokenUtils.getCurrentUser(token);
       int userId=currentUser.getUserId();

       //执行业务
        List<Auth>authTreeList= authService.authTreeByUid(userId);

        //响应
        return Result.ok(authTreeList);
    }

    //退出登录:从redis中删除登录用户的token文件
    @RequestMapping("/logout")
    public Result logout(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME)String token)
    {
        //从redis中删除token的键
        redisTemplate.delete(token);
        //响应
        return Result.ok("退出系统");

    }

}
