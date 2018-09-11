package com.gino.transaction.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author gino
 * Created on 2018/9/11
 */
@Entity
@Table(name = "t_money", schema = "demo")
public class TMoneyEntity {
    private int id;
    private BigDecimal money;
    private String name;

    public TMoneyEntity() {
    }

    public TMoneyEntity(int id, BigDecimal money, String name) {
        this.id = id;
        this.money = money;
        this.name = name;
    }

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "money")
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TMoneyEntity that = (TMoneyEntity) o;
        return id == that.id &&
                Objects.equals(money, that.money) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, money, name);
    }
}
