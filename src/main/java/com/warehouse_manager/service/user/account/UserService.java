package com.warehouse_manager.service.user.account;

/*
* user_info表的service接口*/

import com.warehouse_manager.dto.AssignRoleDto;
import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.pojo.user.User;

import java.util.List;

public interface UserService {
    //根据账号查询用户的业务
    public User queryUserByCode(String userCode);

    //分页查询用户的业务方法
    public Page queryUserByPage(Page page,User user);

    //添加用户
    public Result saveUser(User user);

    //启用或禁用用户的业务方法
    public Result setUserState(User user);

    //给用户分配角色的业务
    public void assginRole(AssignRoleDto assignRoleDto);

    //删除用户的业务方法
    public Result removeUserByIds(List<Integer> userIdList);

    //修改用户的业务方法
    public Result updateUserById(User user);

    //根据用户id响应用户密码的方法
    public Result setPwdById(Integer userId);

}
