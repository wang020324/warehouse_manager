package com.warehouse_manager.pojo.store;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("out_store")
public class OutStore {
    @TableId(type= IdType.AUTO)
    private Integer outsId;
    private Integer productId;
    private Integer storeId;
    private Integer tallyId;
    private BigDecimal outPrice;
    private Integer outNum;
    private Integer createBy;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    private String isOut;
    //追加属性
    private BigDecimal  salePrice;//接收商品的售价，即作为初步价格


    private String productName;//商品名称
    private String startTime;//起始时间
    private String endTime;//结束时间
    private String storeName;//仓库名称
    private String userCode;//创建人


}
