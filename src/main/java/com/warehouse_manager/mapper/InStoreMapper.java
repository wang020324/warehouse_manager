package com.warehouse_manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.store.InStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper

public interface InStoreMapper extends BaseMapper<InStore> {
    //添加入库单(根据采购单进行添加)
    public int insertInStore(InStore inStore);

    //查询入库单行数的方法
    public Integer findInStoreCount(InStore inStore);
    //分页查询入库单的方法
    //1.分页查询，limit条件需要行数 2.有可能条件查询有可能全查询，因此需要封装了条件的对象
    public List<InStore> findInStorePage(@Param("page") Page page, @Param("inStore") InStore inStore);

    //根据id修改入库单状态
    public int setIsInById(Integer inStoreId);
}
