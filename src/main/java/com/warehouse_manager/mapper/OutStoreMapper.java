package com.warehouse_manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.store.OutStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OutStoreMapper extends BaseMapper<OutStore> {
    //由于做了追加，故不能用mybatis-plus接口
    //添加出库单
    public int insertOutStore(OutStore outStore);

    //查询出库单行数
    public Integer findOutStoreCount(OutStore outStore);
    //分页查询
    public List<OutStore> findOutStorePage(@Param("page") Page page, @Param("outStore") OutStore outStore);

    //修改出库单状态
    public int setIsOutById(Integer outStoreId);
}
