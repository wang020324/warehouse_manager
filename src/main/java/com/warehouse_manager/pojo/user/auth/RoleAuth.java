package com.warehouse_manager.pojo.user.auth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("role_auth")
public class RoleAuth {
    @TableId(type= IdType.AUTO)
    private Integer roleAuthId;
    private Integer roleId;
    private Integer authId;
}
