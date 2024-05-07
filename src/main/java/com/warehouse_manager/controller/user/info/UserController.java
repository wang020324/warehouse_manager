package com.warehouse_manager.controller.user.info;

import com.warehouse_manager.dto.AssignRoleDto;
import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.role.Role;
import com.warehouse_manager.pojo.user.CurrentUser;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.pojo.user.User;
import com.warehouse_manager.service.role.RoleService;
import com.warehouse_manager.service.user.account.UserService;
import com.warehouse_manager.utils.TokenUtils;
import com.warehouse_manager.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {
    //注入UserService
    @Autowired
    private UserService userService;
    //分页查询用户的url接口
    @RequestMapping("/user-list")
    public Result userList(Page page, User user){
            //执行业务
           userService.queryUserByPage(page,user);
           //响应
           return Result.ok(page);
    }

    //添加用户的url接口
    /*
    参数用来接收并且封装的json数据的用户信息
    * */
    //注入tokenutils
    @Autowired
    private TokenUtils tokenUtils;
    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME)String token){
         //拿到当前登录的用户ID
        CurrentUser currentUser=tokenUtils.getCurrentUser(token);
        int createBy=currentUser.getUserId();
        user.setCreateBy(createBy);

        //执行业务
        Result result =userService.saveUser(user);

        //响应
        return result;

    }

    //启用或禁用url接口
    @RequestMapping("updateState")
    public Result updateUserState(@RequestBody User user){
           //执行业务
        Result result=  userService.setUserState(user);
          //响应
        return result;
    }
    //查询用户已经分配的角色url接口:
    //参数表示将路径占位符userId的值赋值给请求处理方法入参变量userId
   //注入roleService
    @Autowired
    private RoleService roleService;
    @RequestMapping("/user-role-list/{userId}")
    public Result userRoleList(@PathVariable Integer userId){
           //执行业务
           List<Role>roleList =roleService.queryUserRoleByUid(userId);
        //响应
        return Result.ok(roleList);


    }
    //用户分配角色
    @RequestMapping("/assignRole")
    public Result assignRole(@RequestBody  AssignRoleDto assignRoleDto){
        //直接重新分配，如果去判断现在哪些分配了，再加上没分配的会很麻烦
        //需要三个实现，首先先删除角色用户的所有分配角色，添加用户角色关系，根据角色名查询角色id的方法
        //执行业务
        userService.assginRole(assignRoleDto);
        //响应
        return Result.ok("分配角色成功");


    }

    //根据用户id删除单个用户的url接口
    @RequestMapping("/deleteUser/{userId}")
    public Result deleteUserById(@PathVariable Integer userId){
        //执行业务
       Result result = userService.removeUserByIds(Arrays.asList(userId));//asList会生成一个List，传过去的参数会成为初始化的参数
        return result;
    }
    //根据用户ids批量删除用户的url接口
  @RequestMapping("/deleteUserList")
  public Result deleteUserByIds(@RequestBody  List<Integer> userIdList){
        //执行业务
      Result result =userService.removeUserByIds(userIdList);
      //响应
      return result;
  }

  //修改用户
    @RequestMapping("/updateUser")
    public Result updateUser(@RequestBody User user,@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME)String token){
        //拿到当前登录的用户 id,这样才能根据用户id去更新
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int updateBy  = currentUser.getUserId();
        user.setUpdateBy(updateBy);
        //执行业务
        //Result result=userService.updateUserById(user);
        Result result =userService.updateUserById(user);

        //响应
        return result;

    }

    //重置密码的url接口
    @RequestMapping("/updatePwd/{userId}")
    public Result resetPassword(@PathVariable Integer userId){
        //执行业务
        Result result =userService.setPwdById(userId);
        //响应
        return result;
    }

}
