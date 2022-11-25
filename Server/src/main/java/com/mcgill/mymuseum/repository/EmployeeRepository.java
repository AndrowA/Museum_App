package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.Employee;
import org.springframework.data.repository.CrudRepository;


public interface EmployeeRepository extends CrudRepository<Employee,Long> {
}
