package com.warehouse_manager.controller.commodity;

import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.commodity.*;
import com.warehouse_manager.pojo.user.CurrentUser;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.service.commodity.*;
import com.warehouse_manager.utils.TokenUtils;
import com.warehouse_manager.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    //注入StoreService

    @Autowired
    private StoreService storeService;

    //注入BrandService
    @Autowired
    private BrandService brandService;

    //注入productservice
    @Autowired
    private ProductService productService;

    //注入ProductTypeService
    @Autowired
    private ProductTypeService productTypeService;

    //注入SupplyService
    @Autowired
    private SupplyService supplyService;

    //注入PlaceService
    @Autowired
    private PlaceService placeService;

    @Autowired
    private UnitService unitService;

    //注入token
    @Autowired
    private TokenUtils tokenUtils;

    //查询所有仓库
    @RequestMapping("/store-list")
    public Result storeList(){
        //执行业务
        List<Store>storeList= storeService.queryAllStore();
        //响应
        return  Result.ok(storeList);
    }

    //查询所有品牌
    @RequestMapping("/brand-list")
    public Result brandList(){
        List<Brand>brandList =brandService.queryAllBrand();
        return Result.ok(brandList);
    }

    //分页查询商品:
    @RequestMapping("/product-page-list")
    public Result productListPage(Page page, Product product){
        page=productService.queryProductPage(page,product);
        //响应
        return Result.ok(page);
    }

    //查询所有商品分类树
    @RequestMapping("/category-tree")
    public Result loadTypeTree(){
        //执行业务
        List<ProductType>typeTreeList=productTypeService.productTypeTree();
        //响应
        return Result.ok(typeTreeList);

    }
    //查询所有供应商的url
    @RequestMapping("/supply-list")
    public Result supplyList(){
        //执行业务
        List<Supply>supplyList =supplyService.queryAllSupply();
        //响应
        return Result.ok(supplyList);
    }
    //查询所有产地
    @RequestMapping("/place-list")
    public Result placeList(){
        //执行业务
        List<Place>placeList=placeService.queryAllPlace();
        //响应
        return Result.ok(placeList);

    }


    //查询所有的单位

    @RequestMapping("/unit-list")
    public Result unitList(){
        List<Unit>unitList =unitService.queryAllUnit();
        //响应
        return Result.ok(unitList);
    }

    /*
      上传图片
     参数----表示了封装了请求参数名叫file变量的上传的图片
     */

    //将配置文件中file.upload-path属性注入给控制器的成员属性---图片上传的位置:classpath:static/img/upload
    @Value("${file.upload-path}")
    private String uploadPath;

    @CrossOrigin//表示url接口允许被跨域
    @RequestMapping("/img-upload")
    public Result uploadImage(MultipartFile file){
        try {
            /*
            拿到图片上传的目录路径的file对象,即:classpath:static/img/upload

            因为图片上传到的目录路径是个类路径(resource下的路径/class下的路径,就是带有前缀claspath),
            所以不能直接将路径封装到file对象;使用类路径资源工具类ResourceUtils的方法 getFile()来解析类路径
            并拿到封装了类路径的File对象
             */
            File uploadDirFile=ResourceUtils.getFile(uploadPath);//图片上传的目的路径
            //拿到图片上传到的目录路径的磁盘路径
            String uploadDirPath=uploadDirFile.getAbsolutePath();//拿到磁盘路径
            //拿到上传的图片的名称
            String filename =file.getOriginalFilename();
            String uploadFilePath=uploadDirPath+"\\"+filename;//图片要保存到的磁盘文件的路径
            //上传图片
            file.transferTo(new File(uploadFilePath));//上传的文件保存到的磁盘文件的file对象---实现文件的上传

            //成功响应
            return Result.ok("图片上传成功");

        } catch (Exception e) {
            return Result.err(Result.CODE_ERR_BUSINESS,"图片上传失败");
        }

    }

    //添加商品

    @RequestMapping("/product-add")
    public Result addProduct(@RequestBody  Product product,@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        //拿到当前登录的用户id
       CurrentUser currentUser= tokenUtils.getCurrentUser(token);
       int createBy=currentUser.getUserId();

       product.setCreateBy(createBy);

       //执行业务
       Result result = productService.saveProduct(product);
        //响应
        return result;
    }

    //修改商品上下架状态
    @RequestMapping("/state-change")
    public Result changeProductState(@RequestBody Product product){
        Result result= productService.updateStateByPid(product);
        return result;
    }

    //删除单个商品
    @RequestMapping("/product-delete/{productId}")
    public Result deleteProduct(@PathVariable Integer productId){
        //因为sql查询的业务是合体的，也就是说是将单个查询与集合查询合体，但是这个方法是删除单个商品,传进来的是单个参数
        //因此就将这个id值封装到一个list里
        //执行业务
        Result result =productService.deleteProductByIds(Arrays.asList(productId));
        //响应
        return  result;
    }
    //删除多个商品
    @RequestMapping("/product-list-delete")
    public Result deleteProductList(@RequestBody List<Integer>productIdList){
        //执行业务
        Result result=productService.deleteProductByIds(productIdList);
        //响应
        return result;
    }
    //修改商品
    @RequestMapping("/product-update")
    public Result updateProduct(@RequestBody Product product,@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        //拿到当前登录用户id
        CurrentUser currentUser =tokenUtils.getCurrentUser(token);
        int updateBy=currentUser.getUserId();
        product.setUpdateBy(updateBy);
        //执行业务
        Result result =productService.setProductById(product);
        //响应
        return result;
    }




}
