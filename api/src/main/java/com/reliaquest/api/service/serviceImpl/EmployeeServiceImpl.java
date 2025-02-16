package com.reliaquest.api.service.serviceImpl;

import com.reliaquest.api.dao.EmployeeDao;
import com.reliaquest.api.dto.CreateEmployeeRequestDto;
import com.reliaquest.api.dto.EmployeeResponseDto;
import com.reliaquest.api.exception.NoDataFoundException;
import com.reliaquest.api.exception.ServiceException;
import com.reliaquest.api.service.EmployeeService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeDao employeeDao;

  @Override
  @SneakyThrows
  public List<EmployeeResponseDto> getAllEmployees() {
    log.info("Fetching all employees from DB");
    List<EmployeeResponseDto> employees = this.employeeDao.getAllEmployees();

    if (CollectionUtils.isEmpty(employees)) {
      log.error("No employees found in the database");
      throw new NoDataFoundException("No employee found");
    }

    return employees;
  }

  @Override
  @SneakyThrows
  public List<EmployeeResponseDto> getEmployeesByName(String name) {
    log.info("Fetching employees by name: {}", name);
    List<EmployeeResponseDto> employees = this.employeeDao.getEmployeesByName(name);

    if (CollectionUtils.isEmpty(employees)) {
      log.error("No employees found with the name: {}", name);
      throw new NoDataFoundException("No employee found with name " + name);
    }

    return employees;
  }

  @Override
  @SneakyThrows
  public EmployeeResponseDto getEmployeeById(String id) {
    log.info("Fetching employee with id: {}", id);
    EmployeeResponseDto employee = this.employeeDao.getEmployeeById(id);

    if (Objects.isNull(employee)) {
      log.error("Employee not found with id: {}", id);
      throw new NoDataFoundException("No employee found with id " + id);
    }

    return employee;
  }

  @Override
  @SneakyThrows
  public Integer getHighestSalaryOfEmployees() {
    log.info("Fetching highest salary among employees");
    Integer highestSalary = this.employeeDao.getHighestSalaryOfEmployees();

    if (Objects.isNull(highestSalary) || highestSalary == 0) {
      log.error("No salary data available or no employees found");
      throw new NoDataFoundException("Salary data is unavailable");
    }

    return highestSalary;
  }

  @Override
  @SneakyThrows
  public List<String> getTopTenHighestEarningEmployeeNames() {
    log.info("Fetching top 10 highest earning employees");
    List<String> topEarningEmployees = this.employeeDao.getTopTenHighestEarningEmployeeNames();

    if (CollectionUtils.isEmpty(topEarningEmployees)) {
      log.error("No employees found with salary data");
      throw new NoDataFoundException("Employees not found");
    }

    return topEarningEmployees;
  }

  @Override
  @SneakyThrows
  public EmployeeResponseDto createEmployee(CreateEmployeeRequestDto createEmployeeRequestDto) {
    log.info("Creating a new employee with details: {}", createEmployeeRequestDto);
    EmployeeResponseDto createdEmployee = this.employeeDao.createEmployee(createEmployeeRequestDto);

    if (createdEmployee != null) {
      log.info("Successfully created employee with id: {}", createdEmployee.getId());
    } else {
      log.error("Failed to create employee with details: {}", createEmployeeRequestDto);
      throw new ServiceException("Internal error occurred. Failed to create employee");
    }

    return createdEmployee;
  }

  @Override
  @SneakyThrows
  public boolean deleteEmployeeById(String id) {
    log.info("Attempting to delete employee with id: {}", id);
    boolean isDeleted = this.employeeDao.deleteEmployeeById(id);

    if (isDeleted) {
      log.info("Successfully deleted employee with id: {}", id);
    } else {
      log.error("Failed to delete employee with id: {}", id);
      throw new ServiceException("Internal error occurred. Failed to delete employee");
    }

    return isDeleted;
  }
}
