package com.warehouse_manager.controller.commodity;

import com.warehouse_manager.pojo.commodity.ProductType;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.service.commodity.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/productCategory")
public class ProductTypeController {
    //注入ProductTypeService
    @Autowired
    private ProductTypeService productTypeService;

    //查询商品分类树的url接口
    @RequestMapping("/product-category-tree")
    public Result productCategoryTree(){
        //执行业务
        List<ProductType>typeTreeList= productTypeService.productTypeTree();

        //响应
        return Result.ok(typeTreeList);
    }
    //校验分类编码是否已经存在的url
   @RequestMapping("/verify-type-code")
    public Result checkTypeCode(String typeCode){
        //执行业务
       Result result=productTypeService.checkTypeCode(typeCode);
       //响应
       return result;
   }

   //提交商品分类表单的业务
   @RequestMapping("/type-add")
    public Result addProductType(@RequestBody ProductType productType) {
       //执行业务
    Result result=    productTypeService.saveProductType(productType);
   //响应
       return  result;
   }

   //删除商品分类的业务
    @RequestMapping("/type-delete/{typeId}")
    public Result typeDelete(@PathVariable  Integer typeId){
        //执行业务
        Result result =productTypeService.deleteProductType(typeId);
       //响应
        return result;
    }

    //修改商品分类的业务
    @RequestMapping("/type-update")
    public Result updateProductType(@RequestBody ProductType productType){
        //执行业务
        Result result=productTypeService.setProductType(productType);
        return result;
    }
}
