package com.gino.transaction.service.impl;

import com.gino.transaction.entity.TMoneyEntity;
import com.gino.transaction.repository.TMoneyRepository;
import com.gino.transaction.service.TMoneyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author gino
 * Created on 2018/9/11
 */
@Slf4j
@Service
public class TMoneyServiceImpl implements TMoneyService {
    private final TMoneyRepository repository;

    @Autowired
    public TMoneyServiceImpl(TMoneyRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveWithoutTran() {
        repository.save(new TMoneyEntity(1, BigDecimal.valueOf(100L), "123"));
        repository.save(new TMoneyEntity(2, BigDecimal.valueOf(100L), "456"));
        repository.save(new TMoneyEntity(3, BigDecimal.valueOf(100L), "789"));
    }

    @Override
    public void saveWithTran() {
        repository.save(new TMoneyEntity(1, BigDecimal.valueOf(100L), "123"));
        repository.save(new TMoneyEntity(2, BigDecimal.valueOf(100L), "456"));
        repository.save(new TMoneyEntity(3, BigDecimal.valueOf(100L), "789"));
    }

    @Override
    public void clearAll() {
        repository.deleteAll();
    }

    @Override
    public void updateMoney() {
        TMoneyEntity one = repository.getOne(1);
        TMoneyEntity two = repository.getOne(2);
        one.setMoney(one.getMoney().add(BigDecimal.ONE));
        two.setMoney(two.getMoney().add(BigDecimal.ONE));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("save one");
        repository.save(one);
        log.info("save two");
        repository.save(two);
    }

    @Override
    public void updateMoneyWithLock() {
        TMoneyEntity one = repository.getById(1);
        TMoneyEntity two = repository.getById(2);
        one.setMoney(one.getMoney().add(BigDecimal.ONE));
        two.setMoney(two.getMoney().add(BigDecimal.ONE));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("save one");
        repository.save(one);
        log.info("save two");
        repository.save(two);
    }

    @Override
    public void selectMoney() {
        TMoneyEntity one = repository.selectById(1);
        TMoneyEntity two = repository.selectById(2);
        log.info("one {}", one.getMoney().toString());
        log.info("two {}", two.getMoney().toString());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        one = repository.selectById(1);
        two = repository.selectById(2);
        log.info("one {}", one.getMoney().toString());
        log.info("two {}", two.getMoney().toString());

    }

    @Override
    public void transferMoney(int from, int to) {
        TMoneyEntity one = repository.getOne(from);
        TMoneyEntity two = repository.getOne(to);
        one.setMoney(one.getMoney().subtract(BigDecimal.ONE));
        two.setMoney(two.getMoney().add(BigDecimal.ONE));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("save from:{}", from);
        repository.save(one);
        log.info("save to:{}", to);
        repository.save(two);
    }

    @Override
    public void transferMoneyWithSQL(int from, int to, int qty) {
        repository.updateMoney(from, new BigDecimal(-qty));
        log.info("from:{}", from);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repository.updateMoney(to, new BigDecimal(qty));
        log.info("to:{}", to);
    }
}
