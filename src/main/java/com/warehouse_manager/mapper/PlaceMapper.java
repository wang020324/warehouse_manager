package com.warehouse_manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.warehouse_manager.pojo.commodity.Place;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlaceMapper extends BaseMapper<Place> {
    //查询所有产地
    public List<Place>findAllPlace();
}
