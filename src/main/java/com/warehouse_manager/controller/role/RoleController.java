package com.warehouse_manager.controller.role;

import com.warehouse_manager.dto.AssignAuthDto;
import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.role.Role;
import com.warehouse_manager.pojo.user.CurrentUser;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.service.role.RoleService;
import com.warehouse_manager.utils.TokenUtils;
import com.warehouse_manager.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    //注入RoleService
    @Autowired
    private RoleService roleService;
    //注入tokenUtils
    @Autowired
    private TokenUtils tokenUtils;

    //查询所有角色的url接口
    @RequestMapping("/role-list")
    public Result roleList()
    {
        //执行业务
        List<Role> roleList =roleService.getAllRole();
        //响应
        return Result.ok(roleList);
    }

    //分页查询角色的url接口,Page用来接收请求参数:页码,Role用来接收Role相关数据
    @RequestMapping("/role-page-list")
    public Result roleListPage(Page page,Role role){
        //执行业务
        page =roleService.queryRolePage(page,role);
       //响应结果
        return Result.ok(page);
    }

    //添加角色的接口
    @RequestMapping("/role-add")
    public Result addRole(@RequestBody Role role, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME)String token){
        //拿到当前登录的用户id
      CurrentUser currentUser =tokenUtils.getCurrentUser(token);
      int createBy=currentUser.getUserId();
      Date date =new Date();
      role.setCreateTime(date);

      role.setCreateBy(createBy);//存入当前的创建人id
      //执行业务
       Result result = roleService.saveRole(role);

        //响应

        return result;
    }

    //启用或禁用角色的url
    @RequestMapping("/role-state-update")
    public Result updateRoleState(@RequestBody Role role){
        //执行业务
        Result result =roleService.setRoleState(role);
        //响应
        return result;
    }

    //删除角色的url接口
    @RequestMapping("/role-delete/{roleId}")
    public Result deleteRole(@PathVariable Integer roleId){
        //执行业务
        Result result =roleService.deleteRoleById(roleId);
        //响应
        return result;
    }
    //查询角色分配的所有权限的url
    @RequestMapping("/role-auth")
    public Result roleAuth(Integer roleId){
        //执行业务
        List<Integer>auths =roleService.queryRoleAuthIds(roleId);
        //响应
        return Result.ok(auths);
    }

    //给角色分配菜单权限的url接口
    @RequestMapping("/auth-grant")
    public Result grantAuth(@RequestBody AssignAuthDto assignAuthDto){

      //执行业务
        roleService.saveRoleAuth(assignAuthDto);
        //响应:
        return Result.ok("权限分配成功");
    }


    //修改角色的url
    @RequestMapping("/role-update")
    public Result updateRole(@RequestBody Role role,@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        //拿到当前登录的用户id
        CurrentUser currentUser =tokenUtils.getCurrentUser(token);
        int updateBy=currentUser.getUserId();

        role.setUpdateBy(updateBy);//修改修改人
        //执行
        Result result=roleService.setRoleByRid(role);

        //响应
        return result;
    }
}
