package com.linkaster.timetableService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.linkaster.timetableService.model.Timetable;

/*
 *  Title: TimetableRepository.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Timetable t WHERE t.userOwnerId = :userOwnerId")
    boolean timetableExistsByUserOwnerId(@Param("userOwnerId") Long userOwnerId);

    @Query("SELECT t FROM Timetable t WHERE t.userOwnerId = :userOwnerId")
    Timetable findByUserOwnerId(@Param("userOwnerId") Long userOwnerId);   
}
