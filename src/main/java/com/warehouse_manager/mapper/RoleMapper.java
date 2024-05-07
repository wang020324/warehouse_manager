package com.warehouse_manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.role.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    //查询所有角色(mybatis-plus不然使用不了追加条件)
    public List<Role>findAllRole();
    //根据用户id查询用户分配的所有角色
    public List<Role> findUserRoleByUid(Integer userId);

    //根据角色名查询角色Id的方法
    public Integer findRoleIdByName(String roleName);

    //查询角色行数的方法
    public Integer findRoleRowCount(Role role);
    //分页查询角色的方法
    public List<Role> findRolePage(@Param("page") Page page,@Param("role") Role role);

    //根据角色名称或角色代码查询角色的方法
    public Role findRoleByNameOrCode(String username,String roleCode);
    //添加方法(该部分已使用mybatis-plus代替)
    public int insertRole(Role role);
    //根据角色id修改角色状态
    public int setRoleStateByRid(Integer roleId,String roleState);

    //根据角色id删除角色(已经使用mybatis-plus)
    public int removeRoleById();

    //根据角色id修改角色描述的方法(使用mybatis-plus替代)
    public int setDescByRid(Role role);

}
