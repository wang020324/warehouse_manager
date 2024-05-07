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
@TableName("place")
public class Place implements Serializable {
    @TableId(type= IdType.AUTO)
    private Integer placeId;
    private String placeName;
    private String placeNum;
    private String introduce;
    private String isDelete;

}
