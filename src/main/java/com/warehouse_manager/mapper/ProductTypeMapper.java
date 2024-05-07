package com.warehouse_manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warehouse_manager.pojo.commodity.Product;
import com.warehouse_manager.pojo.commodity.ProductType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductTypeMapper extends BaseMapper<ProductType> {
    //查询所有分类(一旦有追加列就用不了mybatis-plus)
    public List<ProductType> findAllProductType();

    //因为分类编码和分类名称都不能重复，因此根据这一特性，数据只会有一个
    public ProductType findTypeByCodeOrName(ProductType productType);


    //添加商品分类的方法
    public int insertProductType(ProductType productType);

    //根据分类id或者父级分类id删除分类的方法
    public int removeProductType(Integer typeId);

    //根据分类id修改分类信息
    public int setProductTypeById(ProductType productType);



}
