package com.warehouse_manager.pojo.store;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("in_store")
public class InStore {
    private Integer insId;
    private Integer storeId;
    private Integer productId;
    private Integer inNum;
    private Integer createBy;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    private Integer isIn;

    //------------------------追加属性-----
    private String productName;//商品名称
    private String startTime;//起始时间
    private String endTime;//结束时间
    private String storeName;//仓库名称
    private BigDecimal inPrice;//入库价格
    private String UserCode;//创建人
}
