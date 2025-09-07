package com.ams.AMS.repository.leave;

import com.ams.AMS.entities.leave.Leaves;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leaves, Long> {
   Page<Leaves> findLeavesByUserId(Long userId, Pageable pageable);
   Leaves findLeavesById(Long id);

    Page<Leaves> findLeavesByStatus(String status, Pageable pageable);

    Page<Leaves> findLeavesByStartDateAndEndDate(Date startDate, Date endDate, Pageable pageable);
}
