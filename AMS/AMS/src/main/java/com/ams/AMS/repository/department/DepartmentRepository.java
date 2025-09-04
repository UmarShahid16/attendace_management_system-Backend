package com.ams.AMS.repository.department;

import com.ams.AMS.entities.Department.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByDepartmentName(String departmentName);
    Department findDepartmentById(Long departmentId);
}
