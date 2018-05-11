package com.gino.mybatis.mapper;

import com.gino.mybatis.bean.UserBean;

import java.util.List;

/**
 * user mapper
 *
 * @author gino
 * Created on 2018/5/11
 */
public interface UserMapper {
    UserBean selectUserByID(int id);

    List<UserBean> selectUsersByName(String userName);

    void addUser(UserBean user);

    void updateUser(UserBean user);

    int deleteUser(int id);
}
