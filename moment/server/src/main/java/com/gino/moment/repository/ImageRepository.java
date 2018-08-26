package com.gino.moment.repository;

import com.gino.moment.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gino
 * Created on 2018/8/21
 */
@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
    @Query(value = "select distinct userId from ImageEntity")
    List<String> getUserIds();

    @Query(value = "select id from ImageEntity where userId = :userId")
    List<Integer> getIdsByUserId(@Param("userId") String userId);

    List<ImageEntity> getByUserId(String userId);
}
