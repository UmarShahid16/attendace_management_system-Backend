package com.ams.AMS.repository.leave;

import com.ams.AMS.entities.leave.Leaves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends JpaRepository<Leaves, Long> {
}
