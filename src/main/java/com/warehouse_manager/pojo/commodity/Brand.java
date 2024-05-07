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
@TableName("brand")
public class Brand  implements Serializable {
    @TableId(type= IdType.AUTO)
    private Integer brandId;
    private String brandName;
    private String brandLeter;
    private String brandDesc;

}
