package com.warehouse_manager.pojo.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("store")
public class Store implements Serializable {
    @TableId(type= IdType.AUTO)
    private Integer storeId;
    private String storeName;
    private String storeNum;
    private String storeAddress;
    private String concat;
    private String phone;
}
