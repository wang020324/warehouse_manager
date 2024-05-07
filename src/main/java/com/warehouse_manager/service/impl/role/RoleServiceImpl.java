package com.warehouse_manager.service.impl.role;

import com.warehouse_manager.dto.AssignAuthDto;
import com.warehouse_manager.mapper.RoleAuthMapper;
import com.warehouse_manager.mapper.RoleMapper;
import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.user.auth.RoleAuth;
import com.warehouse_manager.pojo.role.Role;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
//2.指定缓存的名称(数据保存到Redis中的键值，一般值给标注@CacheConfig注解类的全路径)
@CacheConfig(cacheNames="com.warehouse_manager.service.impl.role.RoleServiceImpl")
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleAuthMapper roleAuthMapper;

    /*
    查询所有已经启用的角色
     */
    //3.查询方法上标注@Cacheable指定缓存的键
   @Cacheable(key="'all:role'")
    @Override
    public List<Role> getAllRole() {
      /*  QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_state",1);
        return roleMapper.selectList(queryWrapper);*/
        return roleMapper.findAllRole();
    }

    @Override
    public List<Role> queryUserRoleByUid(Integer userId) {
        return roleMapper.findUserRoleByUid(userId);
    }
    //分页查询角色的方法

    @Override
    public Page queryRolePage(Page page, Role role) {
        //查询角色行数
       Integer count = roleMapper.findRoleRowCount(role);
       //分页查询角色
        List<Role> roleList =roleMapper.findRolePage(page,role);
        //组装分页信息
        page.setTotalNum(count);
        page.setResultList(roleList);
        return page;
    }
    //实现添加用户

    @CacheEvict(key="'all:role'")
    @Override
    public Result saveRole(Role role) {
        //在这之前先判断用户是否存在
        Role r =roleMapper.findRoleByNameOrCode(role.getRoleName(),role.getRoleCode());
        if(r!=null)
        {
            //说明角色已经存在
            return Result.err(Result.CODE_ERR_BUSINESS,"角色已存在");
        }
        //角色不存在,则直接添加
        int i =roleMapper.insert(role);
        if(i>0)
        {
            return Result.ok("角色添加成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"角色添加失败");

    }
    //修改角色启用或禁用状态
   @CacheEvict("'all:role'")
    @Override
    public Result setRoleState(Role role) {
        int i = roleMapper.setRoleStateByRid(role.getRoleId(),role.getRoleState());
        if(i>0){
            return Result.ok("角色状态修改成功");

        }
        return Result.err(Result.CODE_ERR_BUSINESS,"角色状态修改失败!");
    }

    //删除角色的业务方法
   //@CacheEvict("'all:role'")
    @Transactional
    @Override
    public Result deleteRoleById(Integer roleId) {
        int i =roleMapper.deleteById(roleId);
        if(i>0)
        {
            return Result.ok("角色删除成功");

        }
        return Result.err(Result.CODE_ERR_BUSINESS,"角色删除失败");
    }

   @CacheEvict("'all:role'")
    //查询角色所对应的权限
    @Override
    public List<Integer> queryRoleAuthIds(Integer roleId) {
        return roleAuthMapper.findAuthIdsByRid(roleId);
    }
    //给角色分配权限的业务方法
    @Transactional//事务处理
    @Override
    public void saveRoleAuth(AssignAuthDto assignAuthDto) {
        //删除角色之前分配的所有权限,有就删除，没有这段就不执行
        roleAuthMapper.removeRoleAuthByRid(assignAuthDto.getRoleId());
        //roleAuthMapper.deleteById(assignAuthDto.getRoleId());

        //添加角色权限关系
        List<Integer>authIdList=assignAuthDto.getAuthIds();//查询所有给角色分配权限的请求的数据
        for(Integer authId:authIdList){
            RoleAuth roleAuth=new RoleAuth();
            roleAuth.setRoleId(assignAuthDto.getRoleId());//设置当前的请求的角色id
            roleAuth.setAuthId(authId);//设置每一个请求的权限id
            roleAuthMapper.insertRoleAuth(roleAuth);
        }
    }

    //修改角色的业务方法
    @CacheEvict(key = "'all:role'")
    @Override
    public Result setRoleByRid(Role role) {
        Date date=new Date();
        role.setUpdateTime(date);
        int i =roleMapper.updateById(role);
        if(i>0){
            return Result.ok("角色修改成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"角色修改失败");
   }
}
