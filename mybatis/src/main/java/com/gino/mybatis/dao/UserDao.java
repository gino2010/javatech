package com.gino.mybatis.dao;

import com.gino.mybatis.bean.UserBean;
import com.gino.mybatis.mapper.UserMapper;
import com.gino.mybatis.util.DBTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * user dao
 *
 * @author gino
 * Created on 2018/5/11
 */
@Slf4j
public class UserDao {

    public UserBean getUserByID(int userID) {
        try (SqlSession session = DBTool.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            UserBean user = userMapper.selectUserByID(userID);
            if (user != null) {
                log.info("User: {}", user.toString());
            }
            return user;
        }
    }

    public void getUserList(String userName) {
        try (SqlSession session = DBTool.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            List<UserBean> users = userMapper.selectUsersByName(userName);
            for (UserBean user : users) {
                log.info("user: {}", user.toString());
            }

        }
    }

    public int addUser(UserBean user) {
        try (SqlSession session = DBTool.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            userMapper.addUser(user);
            session.commit();
            log.info("new user id：" + user.getId());
            return user.getId();
        }
    }

    public void updateUser(UserBean user) {
        try (SqlSession session = DBTool.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            if (user != null) {
                user.setAddress("A new place");
                userMapper.updateUser(user);
                session.commit();
            }
        }
    }

    public void deleteUser(int id) {
        try (SqlSession session = DBTool.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            int i = userMapper.deleteUser(id);
            session.commit();
            log.info("user delete:{}", i);
        }
    }

}
