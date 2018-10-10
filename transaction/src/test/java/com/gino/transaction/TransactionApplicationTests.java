package com.gino.transaction;

import com.gino.transaction.entity.TMoneyEntity;
import com.gino.transaction.repository.TMoneyRepository;
import com.gino.transaction.service.TMoneyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionApplicationTests {

    @Autowired
    TMoneyService service;

    @Autowired
    TMoneyRepository repository;

    @Test
    public void contextLoads() {
    }

    @Test
    // test中添加 此注解，代码不进行commit
    // @Transactional
    public void testTransactional() {
        // 写入一笔数据
        service.saveWithoutTran();
        // 写入0笔数据
        // service.saveWithTran();
    }

    @Test
    public void testClearAll() {
        service.clearAll();
    }

    @Test
    public void testMultipleTran() {
        // 非穿行化隔离，这样只会加1，不是加2，事务交叉了
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<?> submit1 = executorService.submit(() -> service.updateMoney());
        Future<?> submit2 = executorService.submit(() -> service.updateMoney());

        try {
            submit1.get();
            submit2.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            submit1 = executorService.submit(() -> service.updateMoney());
            try {
                submit1.get();
            } catch (InterruptedException | ExecutionException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Test
    public void testMultipleTran2() {
        // 这样只会加1，不是加2，事务交叉了
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<?> submit1 = executorService.submit(() -> service.transferMoney(1, 3));
        Future<?> submit2 = executorService.submit(() -> service.transferMoney(2, 3));

        try {
            submit1.get();
            submit2.get();
        }catch (Exception e){

        }
    }

    @Test
    public void testMultipleTran3() {
        // 这样只会加1，不是加2，事务交叉了
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<?> submit1 = executorService.submit(() -> service.transferMoneyWithSQL(1, 3, 1));
        Future<?> submit2 = executorService.submit(() -> service.transferMoneyWithSQL(3, 2, -1));

        try {
            submit1.get();
            submit2.get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testMultipleTranWithLock() {
        // 锁表可以实现，性能有所损耗
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<?> submit1 = executorService.submit(() -> service.updateMoneyWithLock());
        Future<?> submit2 = executorService.submit(() -> service.updateMoneyWithLock());

        try {
            submit1.get();
            submit2.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateAndSelect() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<?> submit1 = executorService.submit(() -> service.updateMoney());
        Future<?> submit2 = executorService.submit(() -> service.selectMoney());

        try {
            submit1.get();
            submit2.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSelect() {
        service.selectMoney();
    }

    @Test
    @Transactional
    public void testLock() {
        TMoneyEntity byId = repository.getById(1);
        byId.getId();
    }

}
