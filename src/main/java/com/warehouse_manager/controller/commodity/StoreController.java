package com.warehouse_manager.controller.commodity;

import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.commodity.Store;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.service.commodity.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreService storeService;

    //分页查询仓库信息
    @RequestMapping("/store-page-list")
    public Result storePageList(Page page, Store store){
        page = storeService.queryStorePage(page,store);
        //响应
        return Result.ok(page);
    }
    //判断仓库编码是否存在

    @RequestMapping("/store-num-check")
    public Result checkStoreNum(String storeNum)
    {
        //执行业务
        Result result =storeService.checkStoreNum(storeNum);
        //响应
        return result;
    }

    //添加仓库信息
    @RequestMapping("/store-add")
    public Result addStore(@RequestBody Store store){
        Result result=storeService.saveStore(store);
        return result;
    }

    //删除仓库
    @RequestMapping("/store-delete/{storeId}")
    public Result deleteStore(@PathVariable Integer storeId)
    {
        //执行业务
        Result result =
                storeService.deleteStore(storeId);
        //响应
        return result;
    }

    //修改仓库
    @RequestMapping("/store-update")
    public Result updateStore(@RequestBody Store store){
           //执行业务
        Result result=storeService.setStoreById(store);
        //响应
        return result;
    }

    //导出数据
    @RequestMapping("/exportTable")
    public Result exportTable(Page page,Store store){
        //分页查询仓库
        page=storeService.queryStorePage(page,store);
        //拿到当前页数据
        List<?> resultList=page.getResultList();
        //响应
        return Result.ok(resultList);
    }
}
