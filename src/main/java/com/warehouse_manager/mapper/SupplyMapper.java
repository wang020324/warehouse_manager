package com.warehouse_manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warehouse_manager.pojo.commodity.Supply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SupplyMapper extends BaseMapper<Supply> {
    //查询所有供应商
    public List<Supply> findAllSupply();
}
