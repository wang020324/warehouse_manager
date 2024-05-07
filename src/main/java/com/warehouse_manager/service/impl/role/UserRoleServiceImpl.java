package com.warehouse_manager.service.impl.role;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse_manager.mapper.UserRoleMapper;
import com.warehouse_manager.pojo.role.UserRole;
import com.warehouse_manager.service.role.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;



}
