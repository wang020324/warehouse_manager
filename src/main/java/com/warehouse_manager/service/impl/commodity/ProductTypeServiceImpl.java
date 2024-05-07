package com.warehouse_manager.service.impl.commodity;

import com.warehouse_manager.mapper.ProductTypeMapper;
import com.warehouse_manager.pojo.commodity.ProductType;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.service.commodity.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@CacheConfig(cacheNames = "com.warehouse_manager.service.impl.commodity.ProductTypeServiceImpl")
@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    private ProductTypeMapper productTypeMapper;
    //@Cacheable(key = "'all:typeTree'")
    @Override
    public List<ProductType> productTypeTree() {
        //1.查出所有商品分类

        List<ProductType>allProductTypeList=productTypeMapper.findAllProductType();
        //将所有商品分类转成商品分类树
        List<ProductType> typeTree=allTypeToTypeTree(allProductTypeList,0);
        return typeTree;
    }
    //将所有商品分类转成商品分类树的递归算法√
    private List<ProductType> allTypeToTypeTree(List<ProductType>typeList,Integer pid){
        //拿到所有一级分类
        List<ProductType>firstLevelType = new ArrayList<>();
        for(ProductType productType :typeList){
            if(productType.getParentId().equals(pid)){
                firstLevelType.add(productType);
            }
        }

        //查出每个一级分类下单所有二级菜单
       //最终所有一级分类对象，都持有保存其所有二级分类的List集合属性
        for(ProductType productType:firstLevelType){
           List<ProductType>secondLevelType= allTypeToTypeTree(typeList,productType.getTypeId());
           productType.setChildProductCategory(secondLevelType);

        }
      return firstLevelType;

    }


    //校验分类编码是否存在的业务方法
    @Override
    public Result checkTypeCode(String typeCode) {
        //根据分类编码查询分类，并判断是否其存在
        //先创建一个分类对象
        ProductType productType = new ProductType();
        productType.setTypeCode(typeCode);
       ProductType prodType = productTypeMapper.findTypeByCodeOrName(productType);
       //无论编码值是否存在，其返回的都是成功的响应对象，编码不存在，则返回给true（说明分类编码可以写）
        //编码存在，则返回给null
        return Result.ok(prodType==null);
    }

    //所有添加、修改，其实都是把前端表单的数据，封装成了工具类对象，为什么？因为修改的好几项数据，都明显的提交在了前端表单中，在前端json数据中，就是以封装的形式呈现的
    //封装成类对象后，给到mapper，让他们去解析出来每一项，从而进行添加、修改

    //productType，其实是要修改要添加的数据对象
    //@CacheEvict(key="'all:typeTree'")

 //添加商品分类的业务方法
    //productType是从前端来的对象，其中输入的json串里面还有分类编码
    //重新创建一个新的类对象，并为其设置类型名，然后通过这个类型来去做查询，这样就去除掉了分类编码对查询的影响
    //为什么要将类型名分出来:因为那边查询语句是or，如果我在这单独判断分类名，输入一个带分类名与分类型号的json，这样会导致查询时，查询出来的结果会是两个条件都有查到
    //为什么查询要用对象查询:因为那边查询将查编号和查名字写成了一个整体，用动态查询，故需要将封装的工具类对象数据传过去，因此传过去的值是Product对象，因此只能封装成product对象
    @Override
    public Result saveProductType(ProductType productType) {
        //分类名称没有焦点事件
        //因此需要在添加分类名称的时候来判断下分类是否存在
        ProductType prodType = new ProductType();
        prodType.setTypeName(productType.getTypeName());//将前端输入的分类名，摘出来放进新对象
        ProductType prodCategory=productTypeMapper.findTypeByCodeOrName(prodType);
        //再对返回值做判断
        if(prodCategory!=null){
            //说明有相同的分类名了
            return Result.err(Result.CODE_ERR_BUSINESS,"分类名称已经存在");

        }
        //分类名称不存在
        //则可以添加分类
        int i =productTypeMapper.insertProductType(productType);
        if(i>0)
        {
            return Result.ok("商品分类添加成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"商品分类添加失败!");
    }

    //删除分类的业务方法
   // @CacheEvict(key="'all:typeTree'")
    @Override
    public Result deleteProductType(Integer typeId) {
         int i = productTypeMapper.removeProductType(typeId);
         if(i>0){
             //说明删除成功
             return Result.ok("商品分类删除成功");
         }
         return Result.err(Result.CODE_ERR_BUSINESS,"商品分类删除失败");
    }

    //修改商品分类信息的实现
   //@CacheEvict(key="'all:typeTree'")
    @Override
    public Result setProductType(ProductType productType) {
        //首先先判断修改的分类名称是否存在
        //与上面一样的方法，把名字剥离出来，分到一个新的对象去，让新的对象去做查询
        //然后将对象传过去，做只查名字的查询
        ProductType prodType =new ProductType();
        prodType.setTypeName(productType.getTypeName());
        ProductType prodCategory =productTypeMapper.findTypeByCodeOrName(prodType);//查询出来的数据
        if(prodCategory!=null&&!prodCategory.getTypeId().equals(productType.getTypeId())){
            //除了要保证分类名字以外，同时还要保证去查询出来的那个对象信息里的id与要修改的分类id一致
            return Result.err(Result.CODE_ERR_BUSINESS,"分类名称已经存在");
        }
        //否则分类名称不存在，则可以添加
        int i =productTypeMapper.setProductTypeById(productType);
        if(i>0)
        {
            return Result.ok("商品分类修改成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"商品分类修改失败!");

    }
}
