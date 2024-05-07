package com.warehouse_manager.service.impl.user.auth;

import com.alibaba.fastjson.JSON;
import com.warehouse_manager.mapper.AuthMapper;
import com.warehouse_manager.pojo.user.auth.Auth;
import com.warehouse_manager.service.user.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

//指定缓存的名称(缓存的键值),一般是标注CaCheConfig注解类的全路径
@CacheConfig(cacheNames="com.warehouse_manager.service.impl.user.auth.AuthServiceImpl")
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /*
    查询用户菜单的业务方法:
     一个用户的菜单，一般来说，短期内不会动，为提升查询性能，因此还需要照顾到缓存处理

    向redis缓存---键authTree：userId 值：：菜单树List<Auth>转的json串
    * */
    @Override
    public List<Auth> authTreeByUid(Integer userId) {
        //先从redis中查询缓存
       String authTreeJson=redisTemplate.opsForValue().get("authTree:"+userId);
        if(StringUtils.hasText(authTreeJson)){
            //有文本内容，说明redis中有用户菜单树的缓存
            //将json串转回菜单树List并且返回
            List<Auth>authTreeList=JSON.parseArray(authTreeJson,Auth.class);
            return authTreeList;
        }
        //没有缓存，查询用户权限下的所有菜单
        List<Auth> allAuthList=authMapper.findAuthByUid(userId);
        //将所有菜单List<Auth>转换成List<Auth>
        List<Auth>authTreeList=allAuthToAuthTree(allAuthList,0);
        //向redis中缓存菜单树List<Auth>
       redisTemplate.opsForValue().set("authTree:"+userId,JSON.toJSONString(authTreeList));


        return authTreeList;
    }
    //将所有菜单List<Auth>转换成List<Auth>的递推算法
    private List<Auth> allAuthToAuthTree(List<Auth>allAuthList,Integer pid)
    {
        //查询出所有的一级菜单
        List<Auth>firstLevelAuthList = new ArrayList<>();//所有的二级菜单
        for(Auth auth :allAuthList){
            if(auth.getParentId().equals(pid)){
                firstLevelAuthList.add(auth);
            }
        }
        //拿到每个一级菜单的所有二级菜单
        for(Auth firstAuth : firstLevelAuthList)
        {
          List<Auth>secondLevelAuthList =  allAuthToAuthTree(allAuthList,firstAuth.getAuthId());
          firstAuth.setChildAuth(secondLevelAuthList);
        }
         return firstLevelAuthList;
    }
   //查询所有权限菜单树的业务方法
    //查询方法上标注@CaCheable注解并指定缓存的键
   @Cacheable(key="'all:authTree'")
    @Override
    public List<Auth> allAuthTree() {
        //只需要查出所有的权限菜单
       List <Auth> allAuthList= authMapper.findAllAuth();
        //将所有的权限菜单List<Auth>转换成权限菜单树
        List<Auth>authTreeList = allAuthToAuthTree(allAuthList,0);
        return authTreeList;
    }
}
