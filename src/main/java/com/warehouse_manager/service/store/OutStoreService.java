package com.warehouse_manager.service.store;

import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.store.InStore;
import com.warehouse_manager.pojo.store.OutStore;
import com.warehouse_manager.pojo.user.Result;

public interface OutStoreService {
    //添加出货单
    public Result saveOutStore(OutStore outStore);

    //分页查询出库单
    public Page  queryOutStorePage(Page page,OutStore outStore);


    //确认出库的业务方法
    public Result outStoreConfirm(OutStore outStore);
}
