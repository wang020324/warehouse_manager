package com.warehouse_manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warehouse_manager.pojo.user.auth.RoleAuth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleAuthMapper extends BaseMapper<RoleAuth> {
    //根据角色id查询角色分配的所有菜单权限
    public List<Integer> findAuthIdsByRid(Integer roleId);

    //根据角色id删除角色权限关系的方法
    public int removeRoleAuthByRid(Integer roleId);
    //添加角色权限的关系的方法
    public int insertRoleAuth(RoleAuth roleAuth);
}
