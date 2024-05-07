package com.warehouse_manager.pojo.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//商品列表的工具类
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("product")
public class Product {
    @TableId(type= IdType.AUTO)
    private Integer productId;
    private Integer storeId;
    private String storeName;//追加属性
    private Integer brandId;
    private String brandName;//追加属性
    private  String productName;
    private  String productNum;
    private Integer productInvent;
    private Integer typeId;
    private String typeName;//追加属性
    private Integer supplyId;
    private String supplyName;//追加属性
    private Integer placeId;
    private String placeName;
    private Integer unitId;
    private String unitName;//追加属性
    private String introduce;
    private String upDownState;
    private Double inPrice;
    private Double salePrice;
    private Double memPrice;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;//商品的创建时间

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;//商品的修改时间

    private Integer createBy;//创建商品的用户id

    private Integer updateBy;//修改商品的用户id

    private String imgs;//商品的图片地址

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date productDate;//商品的生产日期

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date suppDate;//商品的保质期

    //追加属性
    private String isOverDate;//非表中字段 -- 商品是否过期,0未过期,1已过期


}
