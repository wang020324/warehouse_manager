package com.warehouse_manager.pojo.role;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("role")
public class Role implements Serializable
{
    private static final long SerialVersionUID=7788999100L;
    @TableId(type= IdType.AUTO)
    private Integer roleId;
    private String roleName;
    private String roleDesc;
    private String roleCode;
    private String roleState;
    private Integer createBy;



    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

    //-------------------------追加属性
    private String getCode;//角色创建人

}
