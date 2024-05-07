package com.warehouse_manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.commodity.Product;
import com.warehouse_manager.pojo.commodity.ProductType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    //但凡有了追加属性，都不敢用mybatis-plus

    //查询商品行数的方法
    public Integer findProductRowCount(Product product);
    //分页查询商品的方法
    public List<Product>findProductPage(@Param("page")Page page,@Param("product") Product product);
   //添加商品的方法（一旦遇上追加属性，就无法添加）
    public int insertProduct(Product product);

    //根据型号查询商品的方法(用不了mybatis-plus，因为找不到返回值为类型的接口)
    public Product findProductByNum(String productNum);

    //根据商品id修改上下架状态
    public int setStateByPid(Integer productId,String upDownState);

    //根据商品id删除商品的方法.参数是存有多个需要删除的id
    public int removeProductByIds(List<Integer> productList);

    //根据商品id修改商品的方法
    public int setProductById(Product product);

    //当入库单添加了之后，需要增加对应产品库存数量
    public int setInventById(Integer productId,Integer invent);

}
