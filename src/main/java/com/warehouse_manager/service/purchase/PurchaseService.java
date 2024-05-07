package com.warehouse_manager.service.purchase;

import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.purchase.Purchase;
import com.warehouse_manager.pojo.user.Result;

public interface PurchaseService {
    //添加采购单的业务方法
    public Result savePurchase(Purchase purchase);

    //分页查询采购单的业务方法
    public Page queryPurchasePage(Page page,Purchase purchase);

    //删除采购单
    public Result deletePurchaseById(Integer buyId);

    //修改采购单
    public Result updatePurchaseById(Purchase purchase);
}
