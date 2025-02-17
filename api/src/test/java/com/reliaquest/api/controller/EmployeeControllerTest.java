package com.reliaquest.api.controller;

import com.reliaquest.api.controller.EmployeeControllerImpl.EmployeeController;
import com.reliaquest.api.dto.CreateEmployeeRequestDtoApi;
import com.reliaquest.api.dto.EmployeeResponseDtoApi;
import com.reliaquest.api.service.EmployeeService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

  @Mock
  private EmployeeService employeeService;

  @InjectMocks
  private EmployeeController employeeController;

  @Test
  void testGetAllEmployees_Success() {
    List<EmployeeResponseDtoApi> employees = List.of(
        EmployeeResponseDtoApi.builder().id(UUID.randomUUID()).name("abc").salary(50000).age(30).title("Developer").build()
    );
    Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);

    ResponseEntity<List<EmployeeResponseDtoApi>> response = employeeController.getAllEmployees();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().size());
  }

  @Test
  void testGetEmployeesByNameSearch_Success() {
    String name = "abc";
    List<EmployeeResponseDtoApi> employees = List.of(
        EmployeeResponseDtoApi.builder().id(UUID.randomUUID()).name(name).salary(50000).age(30).title("Developer").build()
    );
    Mockito.when(employeeService.getEmployeesByName(name)).thenReturn(employees);

    ResponseEntity<List<EmployeeResponseDtoApi>> response = employeeController.getEmployeesByNameSearch(name);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().size());
  }

  @Test
  void testGetEmployeeById_Success() {
    UUID id = UUID.randomUUID();
    EmployeeResponseDtoApi employee = EmployeeResponseDtoApi.builder()
        .id(id)
        .name("abc")
        .salary(50000)
        .age(30)
        .title("Developer")
        .build();
    Mockito.when(employeeService.getEmployeeById(id.toString())).thenReturn(employee);

    ResponseEntity<EmployeeResponseDtoApi> response = employeeController.getEmployeeById(id.toString());

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("abc", response.getBody().getName());
  }

  @Test
  void testGetHighestSalary_Success() {
    int highestSalary = 100000;
    Mockito.when(employeeService.getHighestSalaryOfEmployees()).thenReturn(highestSalary);

    ResponseEntity<Integer> response = employeeController.getHighestSalaryOfEmployees();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(highestSalary, response.getBody());
  }

  @Test
  void testGetTopTenHighestEarningEmployeeNames_Success() {
    List<String> topEarners = List.of("abc", "xyz");
    Mockito.when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(topEarners);

    ResponseEntity<List<String>> response = employeeController.getTopTenHighestEarningEmployeeNames();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(2, response.getBody().size());
  }

  @Test
  void testCreateEmployee_Success() {
    CreateEmployeeRequestDtoApi createEmployeeRequestDtoApi = CreateEmployeeRequestDtoApi.builder()
        .name("abc")
        .salary(50000)
        .age(30)
        .title("Developer")
        .build();
    EmployeeResponseDtoApi createdEmployee = EmployeeResponseDtoApi.builder()
        .id(UUID.randomUUID())
        .name("abc")
        .salary(50000)
        .age(30)
        .title("Developer")
        .build();

    Mockito.when(employeeService.createEmployee(createEmployeeRequestDtoApi)).thenReturn(createdEmployee);

    ResponseEntity<EmployeeResponseDtoApi> response = employeeController.createEmployee(
        createEmployeeRequestDtoApi);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("abc", response.getBody().getName());
  }

  @Test
  void testDeleteEmployee_Success() {
    String id = "1";
    Mockito.when(employeeService.deleteEmployeeById(id)).thenReturn(true);

    ResponseEntity<String> response = employeeController.deleteEmployeeById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Employee deleted successfully", response.getBody());
  }
}
