package com.warehouse_manager.controller.store;

import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.commodity.Store;
import com.warehouse_manager.pojo.store.InStore;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.service.commodity.StoreService;
import com.warehouse_manager.service.store.InStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/instore")
public class InStoreController {
    //注入InStoreService
    @Autowired
    private InStoreService inStoreService;
    //注入StoreService
    @Autowired
    private StoreService storeService;

    //查询所有仓库的url
    @RequestMapping("/store-list")
    public Result storeList(){
        List<Store> storeList=storeService.queryAllStore();
        return Result.ok(storeList);
    }
    //分页查询入库单
    @RequestMapping("/instore-page-list")
    public Result inStoreListPage(Page page, InStore inStore){
         //执行业务
        page=inStoreService.queryInStore(page,inStore);
        //响应
        return Result.ok(page);
    }

    //确认入库单

    @RequestMapping("/instore-confirm")
    public Result confirmInStore(@RequestBody InStore inStore){
        //执行业务
        Result result=inStoreService.inStoreConfirm(inStore);
        //响应
        return result;

    }


}
