package com.warehouse_manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warehouse_manager.page.Page;
import com.warehouse_manager.pojo.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
* user _info表的mapper接口*/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    //根据用户名查找用户的方法
    public User findUserByCode(String userCode);

    //查询用户行数的方法
    public Integer findUserRowCount(User user);

    //根据分页查询用户的方法
    public List<User> findUserByPage(@Param("page") Page page,@Param("user") User user);

    //根据用户ID修改用户状态的方法
    public int setStateByUid(Integer userId,String userState);

    //根据用户ID修改用户删除状态
    public int setIsDeleteByIds(List<Integer> userIdList);

   //根据用户id修改用户昵称的方法(尝试用mybatis-plus结果修改之后查询不到)
    public int setUserNameByUid(User user);

    //根据用户id修改用户密码
    public int setPwdByUid(Integer userId,String password);

}
