package com.ams.AMS.repository.user;

import com.ams.AMS.entities.User.User;
import com.ams.AMS.vo.userVo.UserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findUserById(Long userId);
    User findUserByIsActive(Boolean isActive);

    Page<User> findUserByIsActiveTrue(Pageable pageable);

    @Query(value = "SELECT COUNT(*) AS total_users FROM USER WHERE role_id = 2", nativeQuery = true)
    Long countUsers();

    @Query(value = "SELECT COUNT(*) AS TotalEmployes FROM USER WHERE is_active = true AND role_id = 2", nativeQuery = true)
    Long findAllActiveEmployees();

    @Query(value = "SELECT\n" +
            "    (SELECT COUNT(*) FROM user) AS totalEmployees,\n" +
            "\n" +
            "    (\n" +
            "        SELECT COUNT(DISTINCT a.user_id)\n" +
            "        FROM attendance a\n" +
            "        WHERE DATE(a.created_at) = CURDATE()\n" +
            "    ) AS presentToday,\n" +
            "\n" +
            "    (\n" +
            "        SELECT COUNT(DISTINCT l.user_id)\n" +
            "        FROM leaves l\n" +
            "        WHERE CURDATE() BETWEEN l.start_date AND l.end_date\n" +
            "          AND l.status = 'APPROVED'\n" +
            "    ) AS onLeave", nativeQuery = true)
    List<Object[]> dashboardCount();
}

