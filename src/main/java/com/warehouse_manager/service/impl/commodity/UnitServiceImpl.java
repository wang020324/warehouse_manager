package com.warehouse_manager.service.impl.commodity;

import com.warehouse_manager.mapper.UnitMapper;
import com.warehouse_manager.pojo.commodity.Unit;
import com.warehouse_manager.service.commodity.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

//@CacheConfig(cacheNames = "com.warehouse_manager.service.impl.commdity.UnitServiceImpl")
@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    private UnitMapper unitMapper;

    //@Cacheable(key="'all:unit'")
    @Override
    public List<Unit> queryAllUnit() {
        return unitMapper.selectList(null);
    }
}
