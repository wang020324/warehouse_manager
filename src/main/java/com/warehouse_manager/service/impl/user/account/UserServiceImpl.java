package com.warehouse_manager.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse_manager.dto.AssignRoleDto;
import com.warehouse_manager.mapper.RoleMapper;
import com.warehouse_manager.mapper.UserMapper;
import com.warehouse_manager.mapper.UserRoleMapper;
import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.role.UserRole;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.pojo.user.User;
import com.warehouse_manager.service.user.account.UserService;
import com.warehouse_manager.utils.DigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {




    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public User queryUserByCode(String userCode) {
        return userMapper.findUserByCode(userCode);
    }

    @Override
    public Page queryUserByPage(Page page, User user) {
        //查询用户行数
        Integer count =userMapper.findUserRowCount(user);

        //分页查询用户
       List<User> userList =userMapper.findUserByPage(page,user);

       //组装所有的分页信息
        page.setTotalNum(count);
        page.setResultList(userList);
        return page;
    }

    @Override
    public Result saveUser(User user) {

        //首先先判断账号是否已经存在
        User ConfirmedUser =userMapper.findUserByCode(user.getUserCode());
        if(ConfirmedUser!=null)
        {
            return Result.err(Result.CODE_ERR_BUSINESS,"账号已经存在");
        }
        //对密码做加密处理
       String password= DigestUtil.hmacSign(user.getUserPwd());


        user.setUserPwd(password);
        Date date = new Date();
        user.setCreateTime(date);
        //执行添加操作
        int i=userMapper.insert(user);
        if(i>0)
        {
            //说明添加成功
            return Result.ok("用户添加成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"用户添加失败");

    }

    @Override
    public Result setUserState(User user) {
        int i =userMapper.setStateByUid(user.getUserId(),user.getUserState());
        if(i>0){
            return Result.ok("启用或禁用用户");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"启用或禁用用户失败");
    }

    @Transactional//事务处理
    @Override
    public void assginRole(AssignRoleDto assignRoleDto) {
        QueryWrapper queryWrapper =new QueryWrapper();
        queryWrapper.eq("user_id",assignRoleDto.getUserId());
        userRoleMapper.delete(queryWrapper);

         //说明目前该用户有角色，需要重新添加这些已经有的角色
            List<String> roleNameList =assignRoleDto.getRoleCheckList();
            for(String roleName:roleNameList){
                Integer roleId=roleMapper.findRoleIdByName(roleName);//查询出有当前分配角色请求的用户已选角色名的角色Id
                UserRole userRole =new UserRole();
                userRole.setRoleId(roleId);//然后获取角色id
                userRole.setUserId(assignRoleDto.getUserId());//获取对应的角色分配请求的用户id
                userRoleMapper.insert(userRole);//存进数据库
            }
        //无需返回值，只有说当老用户才能删除，新用户没有权限角色，哪来的删除机会
        //能删除就删除，删除不了，说明是新用户，就是循环为空，那么直接跳出循环，就不需要在循环里给他加回去用户角色
        }


    //删除用户的业务方法
    @Override
    public Result removeUserByIds(List<Integer> userIdList) {
        int i =userMapper.setIsDeleteByIds(userIdList);
        if(i>0)
        {
            //大于0(这里是传值，定向修改，如果修改数大于0，那么一定是成功)
            return  Result.ok("用户删除成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"用户删除失败");
    }

    //实现添加用户的业务方法
    @Override
    public Result updateUserById(User user){
/*
        //获取当前时间
        Date date = new Date();
        user.setUpdateTime(date);
        QueryWrapper queryWrapper =
                new QueryWrapper();
        queryWrapper.eq("user_id",user.getUserId());
        int i =userMapper.update(user,queryWrapper);
        if(i>0){
            return Result.ok("用户修改成功");
        }*/
        int i =userMapper.setUserNameByUid(user);
        if(i>0)
        {
            return Result.ok("用户修改成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"用户修改失败");



    }
    //根据用户id修改密码
    @Override
    public Result setPwdById(Integer userId) {
       String password= DigestUtil.hmacSign("123456");

        //根据用户id修改密码
        int i =userMapper.setPwdByUid(userId,password);
        if(i>0)
        {
            return Result.ok("密码重置成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"密码重置失败");

    }

}

