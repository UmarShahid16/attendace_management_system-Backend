package com.ams.AMS.repository.attendance;

import com.ams.AMS.entities.attendace.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUser_Id(Long userId);

    List<Attendance> findByStatus(String status);

    Attendance findById(long id);

    @Query(value = "select * from attendance where user_id = :userId and created_at =:date ", nativeQuery = true)
    Attendance findUserAttendance(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query(value = "SELECT * FROM attendance WHERE user_id = :userId AND MONTH(created_at) = :month AND YEAR(created_at) = :year", nativeQuery = true)
    List<Attendance> findMonthlyAttendanceByUserId(@Param("userId") Long userId, @Param("month")Integer month,@Param("year") Integer year);

    @Query(value = "SELECT * FROM attendance WHERE DATE(created_at) = :date", nativeQuery = true)
    List<Attendance> findDailyAttendance(@Param("date") String date);

    @Query(value = "SELECT SUM(CASE WHEN STATUS = 'Present' THEN 1 ELSE 0 END) AS PresentCount, SUM(CASE WHEN STATUS = 'Absent' THEN 1 ELSE 0 END) AS AbsentCount, SUM(CASE WHEN STATUS = 'WFH' THEN 1 ELSE 0 END) AS WFHCount, SUM(CASE WHEN STATUS = 'Leave' THEN 1 ELSE 0 END) AS LeaveCount, COUNT(*) AS Total FROM attendance WHERE DATE(created_at) = CURDATE()", nativeQuery = true)
    List<Object []> countAttendanceByCreatedAt();

    @Query(value = "select * from attendance where user_id =:userId and Date(created_at) = current_date and check_out_time is null ", nativeQuery = true)
    Attendance findByUserIdAndDayAndCheckOutTimeIsNull(@Param("userId") Long userId);
}
