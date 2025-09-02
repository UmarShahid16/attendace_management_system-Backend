package com.ams.AMS.repository.attendance;

import com.ams.AMS.entities.attendace.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface attendanceRepository extends JpaRepository<Attendance, Long> {
}
