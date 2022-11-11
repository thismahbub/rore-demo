package com.rore.demo.repository;

import com.rore.demo.entity.Battery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatteryRepository extends ServiceRepository<Battery>{

    @Query(value = "select * from battery where watt >= :start and watt <= :end", nativeQuery = true)
    List<Battery> getBatteryByPostCodeRage(@Param("start") Integer start, @Param("end") Integer end);

}