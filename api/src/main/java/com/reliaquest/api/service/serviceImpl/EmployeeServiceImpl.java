package com.reliaquest.api.service.serviceImpl;

import com.reliaquest.api.dao.EmployeeDao;
import com.reliaquest.api.dto.CreateEmployeeRequestDto;
import com.reliaquest.api.dto.EmployeeResponseDto;
import com.reliaquest.api.service.EmployeeService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeDao employeeDao;

  @Override
  public List<EmployeeResponseDto> getAllEmployees() {
    log.info("Fetching all employees from DB");
    List<EmployeeResponseDto> employees = this.employeeDao.getAllEmployees();

    if (employees.isEmpty()) {
      log.warn("No employees found in the database");
    }

    return employees;
  }

  @Override
  public List<EmployeeResponseDto> getEmployeesByName(String name) {
    log.info("Fetching employees by name: {}", name);
    List<EmployeeResponseDto> employees = this.employeeDao.getEmployeesByName(name);

    if (employees.isEmpty()) {
      log.warn("No employees found with the name: {}", name);
    }

    return employees;
  }

  @Override
  public EmployeeResponseDto getEmployeeById(String id) {
    log.info("Fetching employee with id: {}", id);
    EmployeeResponseDto employee = this.employeeDao.getEmployeeById(id);

    if (Objects.isNull(employee)) {
      log.error("Employee not found with id: {}", id);
      return null; // Alternatively, you could throw a custom exception
    }

    return employee;
  }

  @Override
  public Integer getHighestSalaryOfEmployees() {
    log.info("Fetching highest salary among employees");
    Integer highestSalary = this.employeeDao.getHighestSalaryOfEmployees();

    if (Objects.isNull(highestSalary) || highestSalary == 0) {
      log.warn("No salary data available or no employees found");
    }

    return highestSalary;
  }

  @Override
  public List<String> getTopTenHighestEarningEmployeeNames() {
    log.info("Fetching top 10 highest earning employees");
    List<String> topEarningEmployees = this.employeeDao.getTopTenHighestEarningEmployeeNames();

    if (topEarningEmployees.isEmpty()) {
      log.warn("No employees found with salary data");
    }

    return topEarningEmployees;
  }

  @Override
  public EmployeeResponseDto createEmployee(CreateEmployeeRequestDto createEmployeeRequestDto) {
    log.info("Creating a new employee with details: {}", createEmployeeRequestDto);
    EmployeeResponseDto createdEmployee = this.employeeDao.createEmployee(createEmployeeRequestDto);

    if (createdEmployee != null) {
      log.info("Successfully created employee with id: {}", createdEmployee.getId());
    } else {
      log.error("Failed to create employee with details: {}", createEmployeeRequestDto);
    }

    return createdEmployee;
  }

  @Override
  public boolean deleteEmployeeById(String id) {
    log.info("Attempting to delete employee with id: {}", id);
    boolean isDeleted = this.employeeDao.deleteEmployeeById(id);

    if (isDeleted) {
      log.info("Successfully deleted employee with id: {}", id);
    } else {
      log.error("Failed to delete employee with id: {}", id);
    }

    return isDeleted;
  }
}
