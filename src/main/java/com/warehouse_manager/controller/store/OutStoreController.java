package com.warehouse_manager.controller.store;

import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.commodity.Store;
import com.warehouse_manager.pojo.store.OutStore;
import com.warehouse_manager.pojo.user.CurrentUser;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.service.commodity.StoreService;
import com.warehouse_manager.service.store.OutStoreService;
import com.warehouse_manager.utils.TokenUtils;
import com.warehouse_manager.utils.WarehouseConstants;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/outstore")
public class OutStoreController {
    //注入outStoreService
    @Autowired
    private OutStoreService outStoreService;

    @Autowired
    private TokenUtils tokenUtils;
    //注入StoreService
    @Autowired
    private StoreService storeService;

    //添加出货单
    @RequestMapping("/outstore-add")
    public Result addOutStore(@RequestBody OutStore outStore, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME)String token){
        //添加当前的创建用户
        CurrentUser currentUser =tokenUtils.getCurrentUser(token);
        int creatBy=currentUser.getUserId();
        outStore.setCreateBy(creatBy);

        //执行业务
        Result result =outStoreService.saveOutStore(outStore);

        //响应
        return result;

    }

    //查询所有仓库
    @RequestMapping("/store-list")
    public Result storeList(){
        //执行业务
        List<Store> storeList=storeService.queryAllStore();
        return Result.ok(storeList);
    }
    //分页查询出库单
    @RequestMapping("/outstore-page-list")
    public Result outStoreListPage( Page page, OutStore outStore){
                 page=outStoreService.queryOutStorePage(page,outStore);
                 return Result.ok(page);
    }
    //修改出库状态
    @RequestMapping("/outstore-confirm")
    public Result confirmOutStore(@RequestBody OutStore outStore)
    {
        Result result=outStoreService.outStoreConfirm(outStore);
        return result;
    }
}
