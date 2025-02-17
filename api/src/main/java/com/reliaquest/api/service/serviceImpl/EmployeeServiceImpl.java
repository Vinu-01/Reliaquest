package com.reliaquest.api.service.serviceImpl;

import com.reliaquest.api.dto.CreateEmployeeRequestDtoApi;
import com.reliaquest.api.dto.EmployeeResponseDtoApi;
import com.reliaquest.api.exception.NoDataFoundException;
import com.reliaquest.api.exception.ServiceException;
import com.reliaquest.api.service.EmployeeService;
import com.reliaquest.api.util.RestUtil;
import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
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

  private final RestUtil restUtil;

  @Override
  @SneakyThrows
  public List<EmployeeResponseDtoApi> getAllEmployees() {
    log.info("Fetching all employees");
    List<EmployeeResponseDtoApi> employees = this.restUtil.getAllEmployees().getData();

    if (CollectionUtils.isEmpty(employees)) {
      log.error("No employees found");
      throw new NoDataFoundException("No employee found");
    }

    return employees;
  }

  @Override
  @SneakyThrows
  public List<EmployeeResponseDtoApi> getEmployeesByName(String name) {
    log.info("Fetching employees by name: {}", name);
    List<EmployeeResponseDtoApi> employees = this.restUtil.getAllEmployees().getData().stream().filter(
        employeeResponseDtoApi -> employeeResponseDtoApi.getName().contains(name)).toList();

    if (CollectionUtils.isEmpty(employees)) {
      log.error("No employees found with the name: {}", name);
      throw new NoDataFoundException("No employee found with name " + name);
    }

    return employees;
  }

  @Override
  @SneakyThrows
  public EmployeeResponseDtoApi getEmployeeById(String id) {
    log.info("Fetching employee with id: {}", id);
    EmployeeResponseDtoApi employee = this.restUtil.getEmployeeById(UUID.fromString(id)).getData();

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
    Integer highestSalary = this.restUtil.getAllEmployees().getData()
        .stream()
        .map(EmployeeResponseDtoApi::getSalary).max(Integer::compareTo).orElse(null);

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
    List<String> topEarningEmployees = this.restUtil.getAllEmployees().getData().stream()
        .sorted(Comparator.comparingInt(EmployeeResponseDtoApi::getSalary).reversed())
        .limit(10)
        .map(EmployeeResponseDtoApi::getName)
        .collect(Collectors.toList());

    if (CollectionUtils.isEmpty(topEarningEmployees)) {
      log.error("No employees found with salary data");
      throw new NoDataFoundException("Employees not found");
    }

    return topEarningEmployees;
  }

  @Override
  @SneakyThrows
  public EmployeeResponseDtoApi createEmployee(CreateEmployeeRequestDtoApi createEmployeeRequestDtoApi) {
    log.info("Creating a new employee with details: {}", createEmployeeRequestDtoApi);
    EmployeeResponseDtoApi createdEmployee = this.restUtil.createEmployee(createEmployeeRequestDtoApi).getData();

    if (createdEmployee != null) {
      log.info("Successfully created employee with id: {}", createdEmployee.getId());
    } else {
      log.error("Failed to create employee with details: {}", createEmployeeRequestDtoApi);
      throw new ServiceException("Internal error occurred. Failed to create employee");
    }

    return createdEmployee;
  }

  @Override
  @SneakyThrows
  public boolean deleteEmployeeById(String id) {
    log.info("Attempting to delete employee with id: {}", id);
    EmployeeResponseDtoApi employeeResponseDtoApi = getEmployeeById(id);
    boolean isDeleted = this.restUtil.deleteEmployeeByName(employeeResponseDtoApi.getName()).getData();

    if (isDeleted) {
      log.info("Successfully deleted employee with id: {}", id);
    } else {
      log.error("Failed to delete employee with id: {}", id);
      throw new ServiceException("Internal error occurred. Failed to delete employee");
    }

    return isDeleted;
  }
}
