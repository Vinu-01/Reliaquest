package com.reliaquest.api.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.reliaquest.api.controller.EmployeeControllerImpl.EmployeeController;
import com.reliaquest.api.dto.CreateEmployeeRequestDtoApi;
import com.reliaquest.api.dto.EmployeeResponseDtoApi;
import com.reliaquest.api.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

  @Mock
  private EmployeeService employeeService;

  @InjectMocks
  private EmployeeController employeeController;

  private EmployeeResponseDtoApi employee;

  @BeforeEach
  void setUp() {
    // Setup a sample EmployeeResponseDtoApi object for tests
    employee = EmployeeResponseDtoApi.builder()
        .id(UUID.randomUUID())
        .name("Vinod")
        .salary(5000)
        .build();
  }

  @Test
  void testGetAllEmployees_Success() {
    List<EmployeeResponseDtoApi> employees = Collections.singletonList(employee);
    when(employeeService.getAllEmployees()).thenReturn(employees);

    ResponseEntity<List<EmployeeResponseDtoApi>> response = employeeController.getAllEmployees();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().size());
    assertEquals("Vinod", response.getBody().get(0).getName());
  }

  @Test
  void testGetEmployeesByNameSearch_Success() {
    List<EmployeeResponseDtoApi> employees = Collections.singletonList(employee);
    String searchString = "Vinod";
    when(employeeService.getEmployeesByName(searchString)).thenReturn(employees);

    ResponseEntity<List<EmployeeResponseDtoApi>> response = employeeController.getEmployeesByNameSearch(searchString);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().size());
    assertEquals("Vinod", response.getBody().get(0).getName());
  }

  @Test
  void testGetEmployeeById_Success() {
    String employeeId = employee.getId().toString();
    when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);

    ResponseEntity<EmployeeResponseDtoApi> response = employeeController.getEmployeeById(employeeId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(employeeId, response.getBody().getId().toString());
  }

  @Test
  void testGetHighestSalaryOfEmployees_Success() {
    int highestSalary = 10000;
    when(employeeService.getHighestSalaryOfEmployees()).thenReturn(highestSalary);

    ResponseEntity<Integer> response = employeeController.getHighestSalaryOfEmployees();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(highestSalary, response.getBody());
  }

  @Test
  void testGetTopTenHighestEarningEmployeeNames_Success() {
    List<String> topEarners = Arrays.asList("Vinod", "Virat", "Rohit");
    when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(topEarners);

    ResponseEntity<List<String>> response = employeeController.getTopTenHighestEarningEmployeeNames();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(3, response.getBody().size());
  }

  @Test
  void testCreateEmployee_Success() {
    CreateEmployeeRequestDtoApi newEmployee = CreateEmployeeRequestDtoApi.builder()
        .name("Virat")
        .salary(6000)
        .build();

    EmployeeResponseDtoApi createdEmployee = EmployeeResponseDtoApi.builder()
        .id(UUID.randomUUID())
        .name("Virat")
        .salary(6000)
        .build();

    when(employeeService.createEmployee(newEmployee)).thenReturn(createdEmployee);

    ResponseEntity<EmployeeResponseDtoApi> response = employeeController.createEmployee(newEmployee);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Virat", response.getBody().getName());
    assertEquals(6000, response.getBody().getSalary());
  }

  @Test
  void testDeleteEmployeeById_Success() {
    String employeeId = employee.getId().toString();
    when(employeeService.deleteEmployeeById(employeeId)).thenReturn("Vinod");

    ResponseEntity<String> response = employeeController.deleteEmployeeById(employeeId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Vinod", response.getBody());
  }

}
