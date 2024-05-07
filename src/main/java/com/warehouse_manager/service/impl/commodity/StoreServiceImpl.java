package com.warehouse_manager.service.impl.commodity;

import com.warehouse_manager.mapper.StoreMapper;
import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.commodity.Store;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.service.commodity.StoreService;
import com.warehouse_manager.vo.StoreCountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

//@CacheConfig(cacheNames = "com.warehouse_manager.service.impl.commodity.StoreServiceImpl")
@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreMapper storeMapper;

    //@Cacheable(key="'all:store'")
    @Override
    public List<Store> queryAllStore() {
        return storeMapper.selectList(null);
    }


    //@Cacheable(key="'all:store'")
    @Override
    public List<StoreCountVo> queryStoreCount() {
        return storeMapper.findStoreCount();
    }


    //分页查询仓库的业务方法


    @Override
    public Page queryStorePage(Page page, Store store) {
        //首先先查询出仓库的总行数
        Integer count =storeMapper.findStorePageCount(store);
        //分页查询仓库
        List<Store> storeList =storeMapper.selectStorePage(page,store);
        //将查询到的页数与仓库列表进行封装
        page.setTotalNum(count);
        page.setResultList(storeList);
        return page;
    }
    //在添加仓库之前，首先先做仓库编码校验


    @Override
    public Result checkStoreNum(String storeNum) {

        Store store = new Store();
        store.setStoreNum(storeNum);
        Store stod=storeMapper.findStoreByCodeOrName(store);
        //无论编码是否存在，其返回的都是成功的响应对象，编码不存在，则返回给true
        //编码如果存在，则返回false
        return Result.ok(stod==null);


    }


    //添加仓库的业务方法
    //首先先创建一个，把名字给单独分出来，因为那边mapper层是动态查询，
    // 因此需要创建对象分出来名字，不然直接把一整个对象传过去，那边的or就会查出来既有满足名字的也有满足编号的，
    // 而不是对应的一个了

    @Override
    public Result saveStore(Store store) {
        //首先需要先再创建一个对象，来判断下，仓库名称是否存在
        Store storeName=new Store();
        storeName.setStoreName(store.getStoreName());
        Store nameCheck=storeMapper.findStoreByCodeOrName(storeName);
        if(nameCheck!=null)
        {
            return Result.err(Result.CODE_ERR_BUSINESS,"仓库名称已经存在");
        }
        int i = storeMapper.insert(store);
        if(i>0)
        {
            return Result.ok("仓库添加成功！");

        }
        return Result.err(Result.CODE_ERR_BUSINESS,"仓库添加失败");
    }


    //根据id删除仓库
    @Override
    public Result deleteStore(Integer storeId) {
        int i =storeMapper.deleteById(storeId);
        if(i>0)
        {
            return Result.ok("仓库删除成功");

        }
        return Result.err(Result.CODE_ERR_BUSINESS,"仓库删除失败");
    }
    //根据id修改仓库信息的业务方法

    @Override
    public Result setStoreById(Store store) {
        //首先同理先判断仓库名称在数据库中是否已经存在
        //首先需要先再创建一个对象，来判断下，仓库名称是否存在
        Store storeName=new Store();
        storeName.setStoreName(store.getStoreName());
        Store nameCheck=storeMapper.findStoreByCodeOrName(storeName);
        //除了要保证名字不能重名以外，还需要将查询出来的id值，与修改表单传过来的id值保证相等
        if(nameCheck!=null&&!nameCheck.getStoreId().equals(store.getStoreId()))
        {
            return Result.err(Result.CODE_ERR_BUSINESS,"仓库名称已经存在");


        }
        //否则分类名称不存在，则可以添加
        int i =storeMapper.updateById(store);
        if(i>0)
        {

            return Result.ok("仓库修改成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"仓库修改失败");
    }
}
