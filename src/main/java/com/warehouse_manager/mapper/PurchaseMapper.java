package com.warehouse_manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.purchase.Purchase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurchaseMapper extends BaseMapper<Purchase> {
    //添加采购单
    public int insertPurchase(Purchase purchase);

    //查询采购单行数,行数根据采购单的查询情况来确定
    public Integer findPurchaseCount(Purchase purchase);
    //分页查询采购单的方法,查询方法根据具体情况而定，因此需要设置两个变量
    public List<Purchase> findPurchasePage(@Param("page") Page page,@Param("purchase")Purchase purchase);

    //根据id删除采购单
    public int removePurchaseById(Integer buyId);

    //根据id修改预计采购数量
    public int setNumById(Purchase purchase);//数据都封装进了类对象里

    //根据id修改采购单状态为已入库
    public int setIsInById(Integer buyId);

}
