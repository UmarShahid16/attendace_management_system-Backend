package com.ams.AMS.repository.leave;

import com.ams.AMS.entities.leave.LeavesLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveLogRepository extends JpaRepository<LeavesLog, Long> {
    LeavesLog findLeavesLogByUserId(Long userId);
    @Query(value = "SELECT total_leaves, sick_leaves, casual_leaves, earned_leaves, (casual_leaves + earned_leaves + sick_leaves) AS takenLeaves, (total_leaves - (casual_leaves + earned_leaves + sick_leaves)) AS remainingLeaves FROM leaves_log WHERE user_id = :userId", nativeQuery = true)
    List<Object[]> getLeaveBalanceByUserId(Long userId);
}
