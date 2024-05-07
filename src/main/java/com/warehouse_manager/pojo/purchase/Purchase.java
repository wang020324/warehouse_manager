package com.warehouse_manager.pojo.purchase;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("buy_list")
public class Purchase {
    @TableId(type= IdType.AUTO)
    private Integer buyId;
    private Integer productId;
    private Integer storeId;
    private Integer buyNum;
    private Integer factBuyNum;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date buyTime;

    private Integer supplyId;

    private Integer placeId;

    private String buyUser;

    private String phone;

    private String isIn;
    //---------追加属性-------
    private String startTime;//起始时间
    private String endTime;//结束时间
    private String storeName;//仓库名称
    private String productName;//商品名称



}
