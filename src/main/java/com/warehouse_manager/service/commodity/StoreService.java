package com.warehouse_manager.service.commodity;

import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.commodity.ProductType;
import com.warehouse_manager.pojo.commodity.Store;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.vo.StoreCountVo;

import java.util.List;

public interface StoreService {
    //查询所有仓库(已经使用mybatis-plus)
    public List<Store> queryAllStore();
    //为echarts查询每个商品数量的业务方法
    public List<StoreCountVo> queryStoreCount();
    //分页查询入库单的业务方法
    public Page queryStorePage(Page page,Store store);
    //校验商品编码是否存在的业务
    public Result checkStoreNum(String storeNum);
    //添加仓库的业务方法
    public Result saveStore(Store store);

    //根据id删除仓库的业务方法
    public Result deleteStore(Integer storeId);

    //根据id实现修改业务的实现
    //修改仓库分类信息
    public Result setStoreById(Store store);
}
