package com.ams.AMS.repository.attendance;

import com.ams.AMS.entities.attendace.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUser_Id(Long userId);

    List<Attendance> findByStatus(String status);

    Attendance findById(long id);

}
