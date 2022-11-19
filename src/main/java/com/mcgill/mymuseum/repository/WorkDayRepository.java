package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.WorkDay;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface WorkDayRepository extends CrudRepository<WorkDay,Long> {
    Iterable<WorkDay> findWorkDaysByDayAndEmployee_AccountId(Date day, Long employeeId);
    Void deleteWorkDayByDayAndEmployee_AccountId(Date day, Long employeeId);
}
