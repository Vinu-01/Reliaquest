package com.reliaquest.api.dao.daoImpl;

import com.reliaquest.api.dao.EmployeeDao;
import com.reliaquest.api.dto.CreateEmployeeRequestDto;
import com.reliaquest.api.dto.EmployeeResponseDto;
import com.reliaquest.api.model.Employee;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.stream.Collectors;

@Slf4j
@Repository
public class EmployeeDaoImpl implements EmployeeDao {

  private final List<Employee> employeeList = new ArrayList<>();

  @Override
  public List<EmployeeResponseDto> getAllEmployees() {
    log.debug("Fetching all employees.");
    return employeeList.stream()
        .map(Employee::toEmployeeResponseDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<EmployeeResponseDto> getEmployeesByName(String name) {
    log.debug("Fetching employees by name: {}", name);
    List<EmployeeResponseDto> employeeListByName = employeeList.stream()
        .filter(employee -> employee.getName().contains(name))
        .map(Employee::toEmployeeResponseDto)
        .collect(Collectors.toList());
    log.info("Found {} employees with the name containing {}", employeeListByName.size(), name);
    return employeeListByName;
  }

  @Override
  public EmployeeResponseDto getEmployeeById(String id) {
    log.debug("Fetching employee by ID: {}", id);
    Optional<Employee> employeeOpt = employeeList.stream()
        .filter(employee -> employee.getId().toString().equals(id))
        .findFirst();

    if (employeeOpt.isPresent()) {
      log.info("Employee found with ID: {}", id);
      return employeeOpt.get().toEmployeeResponseDto();
    } else {
      log.error("No employee found with ID: {}", id);
      return null;
    }
  }

  @Override
  public Integer getHighestSalaryOfEmployees() {
    log.debug("Calculating highest salary of employees.");
    return employeeList.stream()
        .mapToInt(Employee::getSalary)
        .max()
        .orElse(0);
  }

  @Override
  public List<String> getTopTenHighestEarningEmployeeNames() {
    log.debug("Fetching top 10 highest earning employee names.");
    return employeeList.stream()
        .sorted(Comparator.comparingInt(Employee::getSalary).reversed())
        .limit(10)
        .map(Employee::getName)
        .collect(Collectors.toList());
  }

  @Override
  public EmployeeResponseDto createEmployee(CreateEmployeeRequestDto createEmployeeRequestDto) {
    log.debug("Creating new employee with name: {}", createEmployeeRequestDto.getName());

    Employee employee = createEmployeeRequestDto.fromCreateEmployeeRequestDto().toEmployee();
    boolean isIdAlreadyPresent = employeeList
        .stream()
        .anyMatch(employee1 -> employee1.getId().equals(employee.getId()));

    if (isIdAlreadyPresent) {
      return null;
    }

    employeeList.add(employee);
    return employee.toEmployeeResponseDto();
  }

  @Override
  public boolean deleteEmployeeById(String id) {
    log.debug("Deleting employee with ID: {}", id);
    Optional<Employee> employeeOpt = employeeList.stream()
        .filter(employee -> employee.getId().toString().equals(id))
        .findFirst();

    if (employeeOpt.isPresent()) {
      employeeList.remove(employeeOpt.get());
      log.info("Employee with ID {} has been deleted.", id);
      return true;
    } else {
      log.warn("No employee found to delete with ID: {}", id);
      return false;
    }
  }
}
