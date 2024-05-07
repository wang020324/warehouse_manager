package com.warehouse_manager.service.commodity;

import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.commodity.Product;
import com.warehouse_manager.pojo.user.Result;

import java.util.List;

public interface ProductService {
    //分类查询商品的业务方法
    public Page queryProductPage(Page page, Product product);

    //添加商品的业务方法
    public Result saveProduct(Product product);

    //修改商品上下架
    public Result updateStateByPid(Product product);

    //根据id删除对应的商品
    public Result deleteProductByIds(List<Integer> productIdList);

    //根据id实现修改业务
    public Result setProductById(Product product);

}
