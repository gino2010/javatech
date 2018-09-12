package com.gino.transaction.service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author gino
 * Created on 2018/9/11
 */
public interface TMoneyService {
    void saveWithoutTran();

    @Transactional(rollbackFor = Exception.class)
    void saveWithTran();

    void clearAll();


    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    void updateMoney();

    @Transactional(rollbackFor = Exception.class)
    void updateMoneyWithLock();

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    void selectMoney();
}
