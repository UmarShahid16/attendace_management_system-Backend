package com.ams.AMS.repository.attendance;

import com.ams.AMS.entities.attendace.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUser_Id(Long userId);

    List<Attendance> findByStatus(String status);

    Attendance findById(long id);

    @Query(value = "select * from attendance where user_id = :userId order by id desc limit 1", nativeQuery = true)
    Attendance findUserAttendance(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM attendance WHERE user_id = :userId AND MONTH(created_at) = :month AND YEAR(created_at) = :year", nativeQuery = true)
    List<Attendance> findMonthlyAttendanceByUserId(@Param("userId") Long userId, @Param("month")Integer month,@Param("year") Integer year);

    @Query(value = "SELECT * FROM attendance WHERE DATE(created_at) = :date", nativeQuery = true)
    List<Attendance> findDailyAttendance(@Param("date") String date);

}
