package com.warehouse_manager.controller.user.auth;

import com.warehouse_manager.pojo.user.auth.Auth;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.service.user.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/auth")
@RestController
public class AuthController {
    //注入AuthService
    @Autowired
    private AuthService authService;

    //查询所有菜单树
    @RequestMapping("/auth-tree")
    public Result loadAllAuthTree(){

        List<Auth>allAuthTree=authService.allAuthTree();
        return Result.ok(allAuthTree);
    }
}
