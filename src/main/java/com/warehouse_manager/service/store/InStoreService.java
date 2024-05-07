package com.warehouse_manager.service.store;

import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.store.InStore;
import com.warehouse_manager.pojo.user.Result;

public interface InStoreService {

    //添加入库单的业务方法
  //由于封装传过来的InStore对象里面，没有buyId这一属性，需要给他传过去
    public Result saveInStore(InStore inStore,Integer buyId);

    //分页查询入库单的业务方法
    public Page queryInStore(Page page,InStore inStore);

    //确认入库的业务方法
    public Result inStoreConfirm(InStore inStore);
}
