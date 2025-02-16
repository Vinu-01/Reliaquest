package com.reliaquest.api.controller.EmployeeControllerImpl;

import com.reliaquest.api.controller.EmployeeControllerExpected;
import com.reliaquest.api.dto.CreateEmployeeRequestDto;
import com.reliaquest.api.dto.EmployeeResponseDto;
import com.reliaquest.api.dto.Response;
import com.reliaquest.api.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "EmployeeController", path = "/api/v2/employee")
public class EmployeeControllerExpectedImpl implements
    EmployeeControllerExpected<EmployeeResponseDto, CreateEmployeeRequestDto> {

  private final EmployeeService employeeService;

  @Override
  public ResponseEntity<Response<List<EmployeeResponseDto>>> getAllEmployees() {
    log.info("Request for getting all employees list");
    List<EmployeeResponseDto> employees = employeeService.getAllEmployees();
    if (employees.isEmpty()) {
      log.warn("No employees found");
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content
    }
    return ResponseEntity.ok(Response.handleWithData(employees));
  }

  @Override
  public ResponseEntity<Response<List<EmployeeResponseDto>>> getEmployeesByNameSearch(String searchString) {
    log.info("Request for search employee by name: {}", searchString);
    List<EmployeeResponseDto> employees = employeeService.getEmployeesByName(searchString);
    if (employees.isEmpty()) {
      log.warn("No employees found with name: {}", searchString);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
    }
    return ResponseEntity.ok(Response.handleWithData(employees));
  }

  @Override
  public ResponseEntity<Response<EmployeeResponseDto>> getEmployeeById(String id) {
    log.info("Request for search employee by id: {}", id);
    EmployeeResponseDto employee = employeeService.getEmployeeById(id);
    if (employee == null) {
      log.error("Employee not found with id: {}", id);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
    }
    return ResponseEntity.ok(Response.handleWithData(employee));
  }

  @Override
  public ResponseEntity<Response<Integer>> getHighestSalaryOfEmployees() {
    log.info("Request for highest salary of an employee");
    int highestSalary = employeeService.getHighestSalaryOfEmployees();
    if (highestSalary == 0) {
      log.warn("No salary data available");
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content
    }
    return ResponseEntity.ok(Response.handleWithData(highestSalary));
  }

  @Override
  public ResponseEntity<Response<List<String>>> getTopTenHighestEarningEmployeeNames() {
    log.info("Request for top 10 earning employees list");
    List<String> topEarners = employeeService.getTopTenHighestEarningEmployeeNames();
    if (topEarners.isEmpty()) {
      log.warn("No employees found with salary data");
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content
    }
    return ResponseEntity.ok(Response.handleWithData(topEarners));
  }

  @Override
  public ResponseEntity<Response<EmployeeResponseDto>> createEmployee(CreateEmployeeRequestDto createEmployeeRequestDto) {
    log.info("Request for creating a new employee: {}", createEmployeeRequestDto.getName());
    try {
      EmployeeResponseDto createdEmployee = employeeService.createEmployee(createEmployeeRequestDto);
      log.info("Successfully created employee with id: {}", createdEmployee.getId());
      return ResponseEntity.status(HttpStatus.CREATED).body(Response.handleWithData(createdEmployee)); // 201 Created
    } catch (Exception e) {
      log.error("Error creating employee: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
    }
  }

  @Override
  public ResponseEntity<Response<String>> deleteEmployeeById(String id) {
    log.info("Request for deleting employee with id: {}", id);
    boolean isDeleted = employeeService.deleteEmployeeById(id);
    if (isDeleted) {
      log.info("Successfully deleted employee with id: {}", id);
      return ResponseEntity.ok(Response.handleWithData("Employee deleted successfully"));
    } else {
      log.error("Employee with id: {} not found for deletion", id);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.handleWithData("Employee not found"));
    }
  }
}
