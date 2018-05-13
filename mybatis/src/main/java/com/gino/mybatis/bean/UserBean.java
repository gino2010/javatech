package com.gino.mybatis.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * User
 *
 * @author gino
 * Created on 2018/5/11
 */
@Getter
@Setter
@ToString
public class UserBean implements Serializable {

    private static final long serialVersionUID = -744481534578703243L;

    private Integer id;
    private String name;
    private Integer age;
    private String address;
    private Timestamp createTime;

    public UserBean(Integer id, String name, Integer age, String address, Timestamp createTime) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.createTime = createTime;
    }
}
