package com.warehouse_manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.commodity.ProductType;
import com.warehouse_manager.pojo.commodity.Store;
import com.warehouse_manager.vo.StoreCountVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreMapper extends BaseMapper<Store> {
    //查询所有仓库的方法(已经使用mybatis-plus)
    public List<Store> findAllStore();

    //echart部分，操控图表数据的查询
    //查询每个仓库的商品数量的方法
    public List<StoreCountVo>findStoreCount();

    //查询仓库总行数的方法
    public int  findStorePageCount(Store store);
    //分页查询仓库的方法
    public List<Store> selectStorePage(@Param("page") Page page,@Param("store")Store store);
    //添加仓库的方法(使用mybatis-plus)
    public int insertStore(Store store);

    //根据编码或名称做查询，这样当我单独拿出来判断编号或者判断名称的业务时，都可以动态去查询
    public Store findStoreByCodeOrName(Store store);

    //根据仓库id删除仓库的方法（使用mybatis-plus）
    public int deleteStoreById(Integer storeId);
    //根据id修改仓库的方法
    public int setStoreById(Store store);


}
