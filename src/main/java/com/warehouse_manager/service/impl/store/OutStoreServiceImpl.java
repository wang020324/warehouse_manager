package com.warehouse_manager.service.impl.store;

import com.warehouse_manager.mapper.OutStoreMapper;
import com.warehouse_manager.mapper.ProductMapper;
import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.store.InStore;
import com.warehouse_manager.pojo.store.OutStore;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.service.store.OutStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutStoreServiceImpl implements OutStoreService {
    @Autowired
    private OutStoreMapper outStoreMapper;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public Result saveOutStore(OutStore outStore) {
        int i =outStoreMapper.insertOutStore(outStore);
        if(i>0)
            {
                return Result.ok("添加出库单成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"添加出库单失败");
    }

    //分页查询出库单的业务方法
    @Override
    public Page queryOutStorePage(Page page, OutStore outStore) {
        //首先要查询入库单行数
        Integer count=outStoreMapper.findOutStoreCount(outStore);
        //分页查询入库单
        List<OutStore> outStoreList= outStoreMapper.findOutStorePage(page,outStore);
        //封装配置信息
        page.setTotalNum(count);
        page.setResultList(outStoreList);
        return page;
    }

    @Override
    public Result outStoreConfirm(OutStore outStore) {
        int i =outStoreMapper.setIsOutById(outStore.getOutsId());
         if(i>0)
         {
             //如果出库单状态修改成功,那么就要修改仓库的数据情况
            int j = productMapper.setInventById(outStore.getProductId(),-outStore.getOutNum());
            if(j>0)
            {
                //如果j大于0，则说明，仓库数据变化成功
                return Result.ok("出库单确认成功");
            }
            return Result.err(Result.CODE_ERR_BUSINESS,"出库单确认失败");
         }
         return Result.err(Result.CODE_ERR_BUSINESS,"出库单出库状态修改失败");
    }
}
