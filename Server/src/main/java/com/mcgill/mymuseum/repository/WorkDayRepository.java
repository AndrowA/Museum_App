package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.WorkDay;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface WorkDayRepository extends CrudRepository<WorkDay,Long> {
    List<WorkDay> findWorkDaysByEmployee_AccountId(Long employeeId);
    WorkDay findWorkDayByDayAndEmployeeAccountId(Date day, Long employeeId);
    Integer deleteWorkDayByDayAndEmployeeAccountId(Date day, Long employeeId);
}
