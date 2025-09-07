package com.ams.AMS.repository.holiday;

import com.ams.AMS.entities.holiday.Holidays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holidays, Long> {
    Holidays findHolidaysById(Long id);
}
