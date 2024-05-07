package com.warehouse_manager.service.impl.commodity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse_manager.mapper.SupplyMapper;
import com.warehouse_manager.pojo.commodity.Supply;
import com.warehouse_manager.service.commodity.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

//@CacheConfig(cacheNames = "com.warehouse_manager.service.impl.commodity.SupplyServiceImpl")
@Service
public class SupplyServiceImpl implements SupplyService {
    @Autowired
    private SupplyMapper supplyMapper;
    //@Cacheable(key="'all:supply'")
    @Override
    public List<Supply> queryAllSupply() {
        QueryWrapper queryWrapper =
                 new QueryWrapper();
        queryWrapper.eq("is_delete",0);
        return supplyMapper.selectList(queryWrapper);

    }
}
