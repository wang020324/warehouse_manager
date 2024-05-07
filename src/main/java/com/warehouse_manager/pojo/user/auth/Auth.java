package com.warehouse_manager.pojo.user.auth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*
auth_info菜单表对应的实体类
* * */
@Data
@NoArgsConstructor
@AllArgsConstructor

@TableName("auth_info")
public class Auth implements Serializable {
    private static final long SerialVersionUID=7788999100L;
    @TableId(type= IdType.AUTO)
    private Integer authId;//权限菜单id
    private Integer parentId;//父权限菜单Id
    private String authName;
    private String authDesc;
    private int authGrade;
    private String authType;
    private String authUrl;
    private String authCode;
    private Integer authOrder;
    private String authState;
    private Integer createBy;
    private Date createTime;
    private Integer updateBy;
    private Date updateTime;

    //追加的属性
    //存放当前菜单下的所有子级别菜单
    private List<Auth> childAuth;
}
