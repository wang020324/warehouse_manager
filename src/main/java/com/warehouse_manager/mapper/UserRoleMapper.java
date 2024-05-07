package com.warehouse_manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warehouse_manager.pojo.role.UserRole;
import com.warehouse_manager.pojo.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

  //根据用户id删除用户已经分配的用户角色关系
   // public int removeUserRoleByUid(Integer userId);(调用mybatis-plus，使用QueryWrapper来删除)

  //添加用户角色关系的方法
  //public int insertUserRole(UserRole userRole );//insert into values(null,#{roleId},#{userId})
}
