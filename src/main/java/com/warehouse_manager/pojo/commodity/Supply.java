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
@TableName("supply")
public class Supply implements Serializable {
    @TableId(type= IdType.AUTO)
    private Integer supplyId;
    private String supplyNum;
    private String supplyName;
    private String supplyIntroduce;
    private String concat;
    private String phone;
    private String address;
    private String isDelete;
}
