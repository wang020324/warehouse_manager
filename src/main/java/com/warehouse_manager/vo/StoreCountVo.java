package com.warehouse_manager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//封装每个仓库商品数量的vo类
@Data
@NoArgsConstructor
@AllArgsConstructor

public class StoreCountVo {

    private Integer storeId;//仓库id
    private String storeName;//仓库名称
    private Integer totalInvent;//仓库的商品数量

}
