package com.warehouse_manager.pojo.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
//商品分类的工具类

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("product_type")
public class ProductType  implements  Serializable{

    private static final long serialVersionUID=424066109132997221L;
    @TableId(type= IdType.AUTO)
    private Integer typeId;
    private Integer parentId;
    private String typeCode;
    private String typeName;
    private String typeDesc;

    //自定义List<ProductType>集合属性，用于存储当前分类的所有子级分类
    private List<ProductType> childProductCategory;
}
