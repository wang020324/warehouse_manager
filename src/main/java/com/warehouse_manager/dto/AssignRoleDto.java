package com.warehouse_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
接收分配角色请求的json数据的dto类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignRoleDto {

    private Integer userId;
    private List<String> roleCheckList;
}
