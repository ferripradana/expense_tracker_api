package com.ferri.expensestrackerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ferri.expensestrackerapi.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

}
