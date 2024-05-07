package com.warehouse_manager.service.user.auth;

import com.warehouse_manager.pojo.user.auth.Auth;

import java.util.List;

public interface AuthService {

    //查询用户菜单树的方法
    public List<Auth>authTreeByUid(Integer userId);

    //查询所有权限菜单的业务方法
    public List<Auth> allAuthTree();
}
