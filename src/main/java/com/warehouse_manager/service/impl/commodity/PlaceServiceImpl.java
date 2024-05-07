package com.warehouse_manager.service.impl.commodity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.warehouse_manager.mapper.PlaceMapper;
import com.warehouse_manager.pojo.commodity.Place;
import com.warehouse_manager.service.commodity.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

//@CacheConfig(cacheNames = "com.warehouse_manager.service.impl.commodity.PlaceServiceImpl")
@Service
public class PlaceServiceImpl implements PlaceService {
    @Autowired
    private PlaceMapper placeMapper;
    //@Cacheable(key="'all:place'")
    @Override
    public List<Place> queryAllPlace() {
        QueryWrapper queryWrapper=
                new QueryWrapper();
        queryWrapper.eq("is_delete",0);
        return placeMapper.selectList(queryWrapper);
    }
}
