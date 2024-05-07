package com.warehouse_manager.service.impl.purchase;

import com.warehouse_manager.mapper.PurchaseMapper;
import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.purchase.Purchase;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.service.purchase.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private PurchaseMapper purchaseMapper;
    @Override
    public Result savePurchase(Purchase purchase) {
      /* //补充字段---给fact_buy_num字段实际采购赋值为buy_num字段的值预采购数量。实际的采购订单一开始不能给值，给了值就不对了，等正式确定下采购再说
        purchase.setFactBuyNum(purchase.getBuyNum());*/

        //添加采购单
       int i = purchaseMapper.insertPurchase(purchase);
        if(i>0){
            return Result.ok("采购单添加成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"采购单添加失败");
    }

    //分页查询采购单的业务方法
    @Override
    public Page queryPurchasePage(Page page, Purchase purchase) {
        //查询采购单行数
        Integer count=purchaseMapper.findPurchaseCount(purchase);
        //分页查询采购单
        List<Purchase> purchaseList=purchaseMapper.findPurchasePage(page,purchase);

        //组装分页信息
        page.setTotalNum(count);
        page.setResultList(purchaseList);
        return page;
    }

    //删除采购单的业务方法
    @Override
    public Result deletePurchaseById(Integer buyId) {
        int i =purchaseMapper.removePurchaseById(buyId);
        if(i>0){
            return Result.ok("采购单删除成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"采购单删除失败");
    }


    //根据id修改采购信息
    @Override
    public Result updatePurchaseById(Purchase purchase) {
        int i =purchaseMapper.setNumById(purchase);
        if(i>0)
        {
            return Result.ok("采购信息修改成功");

        }
        return Result.err(Result.CODE_ERR_BUSINESS,"采购信息修改失败");
    }
}
