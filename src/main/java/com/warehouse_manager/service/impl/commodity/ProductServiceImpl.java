package com.warehouse_manager.service.impl.commodity;

import com.warehouse_manager.mapper.ProductMapper;
import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.commodity.Product;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.service.commodity.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Override
    public Page queryProductPage(Page page, Product product) {
        //查询商品的行数
        Integer count =productMapper.findProductRowCount(product);
        //分页查询商品
        List<Product>productList= productMapper.findProductPage(page,product);
        //组装分页信息
        page.setTotalNum(count);
        page.setResultList(productList);
        return page;
    }



    //添加商品
    //上传的图片访问的路径的目录路径
    @Value("${file.access-path}")
    private String fileAccessPath;
    @Override
    public Result saveProduct(Product product) {
        //商品名可以重复，型号不能重复
        //先判断商品的型号是否已经存在
        Product prct =productMapper.findProductByNum(product.getProductNum());
        if(prct!=null){
            //说明商品型号已经存在
            return Result.err(Result.CODE_ERR_BUSINESS,"商品型号已存在！");

        }
        //需要处理product对象里面的图参数类型
        product.setImgs(fileAccessPath+product.getImgs());
        //添加商品
     int i =   productMapper.insertProduct(product);

    if(i>0){
        return Result.ok("添加商品成功");
    }
        return Result.err(Result.CODE_ERR_BUSINESS,"商品添加失败！");
    }

    //修改商品上下架状态的业务方法
    @Override
    public Result updateStateByPid(Product product) {
        int i =productMapper.setStateByPid(product.getProductId(), product.getUpDownState());
        if(i>0){
            return Result.ok("商品的上下架状态修改成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"商品上下架状态修改失败");
    }

    //根据id删除指定的商品
    @Override
    public Result deleteProductByIds(List<Integer> productIdList) {
         int i =productMapper.removeProductByIds(productIdList);
         if(i>0){
             return Result.ok("商品删除成功");
         }
         return Result.err(Result.CODE_ERR_BUSINESS,"商品删除失败");
    }

    //修改商品的业务方法
    @Override
    public Result setProductById(Product product) {
        //product对象，是从前端传来的数据集合，因此，是在当前还未正式提交成功至后台前，查询
        //所有数据库中所有与要修改的型号名一样的Product对象
       Product prod= productMapper.findProductByNum(product.getProductNum());
       //若prod存在，说明存在一个与该修改型号相同的产品,且如果这个prod的id
        // 与表单提交数据的product的id不一样，那么说明已经有该型号产品，修改是失败的
        if(prod!=null&&!prod.getProductId().equals(product.getProductId())){
            return Result.err(Result.CODE_ERR_BUSINESS,"商品型号已经存在");

        }
        //要修改型号的对象product的id与数据库中查询到的相同型号的商品对象id一样，那么就说明没有做出修改
        // 型号不存在，那么则说明可以修改

        //可以修改，则需要判断下上传的图片有没有修改，如果修改了需要处理访问路径
        if(!product.getImgs().contains(fileAccessPath)){
            product.setImgs(fileAccessPath+product.getImgs());
        }

        //修改商品
        int i =productMapper.setProductById(product);
        if(i>0)
        {
            return Result.ok("商品修改成功");
        }

      return Result.err(Result.CODE_ERR_BUSINESS,"商品修改失败");
    }

}
