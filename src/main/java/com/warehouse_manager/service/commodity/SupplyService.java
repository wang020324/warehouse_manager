package com.warehouse_manager.service.commodity;

import com.warehouse_manager.pojo.commodity.Supply;

import java.util.List;

public interface SupplyService {
    //查询所有供应商
    public List<Supply>queryAllSupply();
}
