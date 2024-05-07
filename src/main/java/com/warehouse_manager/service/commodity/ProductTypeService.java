package com.warehouse_manager.service.commodity;

import com.warehouse_manager.pojo.commodity.ProductType;
import com.warehouse_manager.pojo.user.Result;

import java.util.List;

public interface ProductTypeService {
    //查询所有商品分类
    public List<ProductType> productTypeTree();

    //校验分类编码是否存在的业务方法
    public Result checkTypeCode(String typeCode);

    //添加商品分类的业务
    public Result saveProductType(ProductType productType);

    //删除商品分类业务
    public Result deleteProductType(Integer typeId);

    //修改商品分类信息
    public Result setProductType(ProductType productType);


}
