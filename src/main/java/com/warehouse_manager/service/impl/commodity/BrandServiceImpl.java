package com.warehouse_manager.service.impl.commodity;

import com.warehouse_manager.mapper.BrandMapper;
import com.warehouse_manager.pojo.commodity.Brand;
import com.warehouse_manager.service.commodity.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

//@CacheConfig(cacheNames = "'com.warehouse_manager.service.impl.commodity.BrandServiceImpl'")
@Service
public class BrandServiceImpl implements BrandService {
   @Autowired
   private BrandMapper brandMapper;

   //@Cacheable(key="'all:brand'")
    @Override
    public List<Brand> queryAllBrand() {
        return brandMapper.selectList(null);
    }
}
