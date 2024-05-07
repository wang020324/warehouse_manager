package com.warehouse_manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warehouse_manager.pojo.commodity.Unit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UnitMapper extends BaseMapper<Unit> {
    //查询所有单位(已经用mybatis-plus)

    public List<Unit> findAllUnit();
}
