package com.warehouse_manager.pojo.role;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_role")
public class UserRole {
    @TableId(type= IdType.AUTO)
    private Integer userRoleId;
    private Integer roleId;
    private Integer userId;
}
