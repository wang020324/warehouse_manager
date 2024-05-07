package com.warehouse_manager.service.impl.store;

import com.baomidou.mybatisplus.annotation.TableName;
import com.warehouse_manager.mapper.InStoreMapper;
import com.warehouse_manager.mapper.ProductMapper;
import com.warehouse_manager.mapper.PurchaseMapper;
import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.store.InStore;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.service.commodity.ProductService;
import com.warehouse_manager.service.store.InStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Service
public class InStoreServiceImpl implements InStoreService {
    @Autowired
    private InStoreMapper inStoreMapper;

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Autowired
    private ProductMapper productMapper;
    //添加入库单的业务方法
    @Transactional//事务业务注解
    @Override
    public Result saveInStore(InStore inStore,Integer buyId) {
        //首先先添加入库单
        Date date=new Date();
        inStore.setCreateTime(date);
       // int i =inStoreMapper.insert(inStore);
        int i =inStoreMapper.insertInStore(inStore);
        if(i>0)
        {
            //入库单添加成功
            //修改采购单状态为入库
            int j =purchaseMapper.setIsInById(buyId);
            if(j>0)
            {
               return  Result.ok("入库单添加成功");
            }
            //如果采购单的状态没有修改，那么其实还是属于没有添加成功
            return Result.err(Result.CODE_ERR_BUSINESS,"入库单添加失败");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"入库单添加失败");

    }

    //分页查询入库单的业务方法
    @Override
    public Page queryInStore(Page page, InStore inStore) {
        //首先要查询入库单行数
        Integer count=inStoreMapper.findInStoreCount(inStore);
        //分页查询入库单
        List<InStore>inStoreList= inStoreMapper.findInStorePage(page,inStore);
        //封装配置信息
        page.setTotalNum(count);
        page.setResultList(inStoreList);
        return page;
    }

    //确认入库单的具体业务
    @Transactional//事务业务注解
    @Override
    public Result inStoreConfirm(InStore inStore) {
        //先修改入库单状态
      int i =  inStoreMapper.setIsInById(inStore.getInsId());//InsId是入库单id
        if(i>0){
            //入库单注入如果成功，需要修改仓库产品库存
            //传入instore对象里封装的对应入库单的商品id数据、商品入库数量的数据
            int j=productMapper.setInventById(inStore.getProductId(),inStore.getInNum());
            if(j>0){
                //如果入库单注入成功，同时仓库产品库存修改成功，那么入库单确认成功
                return Result.ok("入库单确认成功");
            }
            return Result.err(Result.CODE_ERR_BUSINESS,"入库单状态确认失败");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"入库单状态修改失败");

    }
}
