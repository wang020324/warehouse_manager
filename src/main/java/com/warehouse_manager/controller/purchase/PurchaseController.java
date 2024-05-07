package com.warehouse_manager.controller.purchase;

import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.commodity.Store;
import com.warehouse_manager.pojo.purchase.Purchase;
import com.warehouse_manager.pojo.store.InStore;
import com.warehouse_manager.pojo.user.CurrentUser;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.service.commodity.StoreService;
import com.warehouse_manager.service.purchase.PurchaseService;
import com.warehouse_manager.service.store.InStoreService;
import com.warehouse_manager.utils.TokenUtils;
import com.warehouse_manager.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/purchase")
@RestController
public class PurchaseController {
    //注入PurchaseService
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private StoreService storeService;

    //注入InstoreService
    @Autowired
    private InStoreService inStoreService;
    //注入用户id
    @Autowired
    private TokenUtils tokenUtils;
    //添加采购单
    @RequestMapping("/purchase-add")
    public Result addPurchase(@RequestBody Purchase purchase){
         //执行业务
       Result result = purchaseService.savePurchase(purchase);
       //响应
        return result;
    }
    //查询仓库列表
    @RequestMapping("/store-list")
    public Result storeList(){
        //执行业务
        List<Store> storeList=storeService.queryAllStore();
        //响应
        return Result.ok(storeList);
    }

    //分页查询采购单
    @RequestMapping("/purchase-page-list")
    public Result purchaseListPage(Page page,Purchase purchase){
         //执行业务
        page=purchaseService.queryPurchasePage(page,purchase);
        //响应
        return Result.ok(page);
    }

    //删除采购单
    @RequestMapping("/purchase-delete/{buyId}")
    public Result deletePurchase(@PathVariable  Integer buyId){
        //直接执行业务
        Result result=purchaseService.deletePurchaseById(buyId);
        //响应
        return result;
    }

    //修改采购单

    @RequestMapping("/purchase-update")
    public Result updatePurchase(@RequestBody Purchase purchase){
       //执行业务
       Result result = purchaseService.updatePurchaseById(purchase);
        //响应
        return result;
    }

    //生成入库单的url
    @RequestMapping("/in-warehouse-record-add")
    public Result addInStore(@RequestBody Purchase purchase,@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String  token){
            //执行业务
        //拿到当前登录用户的id
        CurrentUser currentUser =tokenUtils.getCurrentUser(token);
        int createBy=currentUser.getUserId();
        //因为需要传入InStore对象，但是这里面没有InStore对象，因此需要传入
        InStore inStore = new InStore();
        //同时，采购单的添加人，也就是当前的登录用户，故直接获取id
        inStore.setCreateBy(createBy);

        //因为入库数量。实际上就是采购单的输入的实际数量，因此需要给入库数量赋值
        //同时仓库id，就是当前采购的输入的storeId
        //产地id，就是当前采购单里的id
        inStore.setStoreId(purchase.getStoreId());
         inStore.setProductId(purchase.getProductId());
          inStore.setInNum(purchase.getFactBuyNum());

          //执行业务
        Result result=inStoreService.saveInStore(inStore,purchase.getBuyId());
        return result;
    }
}
