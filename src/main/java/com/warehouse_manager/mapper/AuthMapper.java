package com.warehouse_manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warehouse_manager.pojo.user.auth.Auth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthMapper extends BaseMapper<Auth> {

    //根据用户id查询用户权限下的所有菜单
    public List<Auth> findAuthByUid(Integer userId);

    //查询所有的权限菜单
    public List<Auth> findAllAuth();

    //根据角色id查询分配的所有权限菜单的方法


}
