package com.warehouse_manager.service.role;

import com.warehouse_manager.dto.AssignAuthDto;
import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.role.Role;
import com.warehouse_manager.pojo.user.Result;

import java.util.List;

public interface RoleService {
   public List<Role> getAllRole();

   //根据用户id查询用户已分配的角色业务方法
    public List<Role>queryUserRoleByUid(Integer userId);

    //分页查询角色的业务方法
    public Page queryRolePage(Page page,Role role);

    //添加角色的业务方法
    public Result saveRole(Role role);

    //实现修改角色状态的方法
    public Result setRoleState(Role role);

    //实现删除角色的业务方法
    public Result deleteRoleById(Integer roleId);

    //查询角色分配的所有的权限菜单的id
    public List<Integer>queryRoleAuthIds(Integer roleId);


    //为角色分配权限的业务方法
 public void saveRoleAuth(AssignAuthDto assignAuthDto);

    //修改角色的业务方法
     public Result setRoleByRid(Role role);

}
