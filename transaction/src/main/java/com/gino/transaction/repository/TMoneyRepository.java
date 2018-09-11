package com.gino.transaction.repository;

import com.gino.transaction.entity.TMoneyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;

/**
 * @author gino
 * Created on 2018/9/11
 */
public interface TMoneyRepository extends JpaRepository<TMoneyEntity, Integer> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    TMoneyEntity getById(Integer id);

    @Query(value = "select e from TMoneyEntity e where e.id=:id")
    TMoneyEntity selectById(@Param("id") Integer id);
}
