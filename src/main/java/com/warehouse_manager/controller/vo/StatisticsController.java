package com.warehouse_manager.controller.vo;

import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.service.commodity.StoreService;
import com.warehouse_manager.vo.StoreCountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    //注入storeService
    @Autowired
    private StoreService storeService;

    //统计仓库数据的业务
    @RequestMapping("/store-invent")
    public Result storeInvent(){
        List<StoreCountVo>storeCountVoList=storeService.queryStoreCount();
        return Result.ok(storeCountVoList);
    }
}
