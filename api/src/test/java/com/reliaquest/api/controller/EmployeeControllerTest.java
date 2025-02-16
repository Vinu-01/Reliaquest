package com.reliaquest.api.controller;

import com.reliaquest.api.controller.EmployeeControllerImpl.EmployeeController;
import com.reliaquest.api.dto.CreateEmployeeRequestDto;
import com.reliaquest.api.dto.EmployeeResponseDto;
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
    List<EmployeeResponseDto> employees = List.of(
        EmployeeResponseDto.builder().id(UUID.randomUUID()).name("abc").salary(50000).age(30).title("Developer").build()
    );
    Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);

    ResponseEntity<List<EmployeeResponseDto>> response = employeeController.getAllEmployees();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().size());
  }

  @Test
  void testGetAllEmployees_NoContent() {
    Mockito.when(employeeService.getAllEmployees()).thenReturn(List.of());

    ResponseEntity<List<EmployeeResponseDto>> response = employeeController.getAllEmployees();

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  @Test
  void testGetEmployeesByNameSearch_Success() {
    String name = "abc";
    List<EmployeeResponseDto> employees = List.of(
        EmployeeResponseDto.builder().id(UUID.randomUUID()).name(name).salary(50000).age(30).title("Developer").build()
    );
    Mockito.when(employeeService.getEmployeesByName(name)).thenReturn(employees);

    ResponseEntity<List<EmployeeResponseDto>> response = employeeController.getEmployeesByNameSearch(name);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().size());
  }

  @Test
  void testGetEmployeesByNameSearch_NotFound() {
    String name = "abc";
    Mockito.when(employeeService.getEmployeesByName(name)).thenReturn(List.of());

    ResponseEntity<List<EmployeeResponseDto>> response = employeeController.getEmployeesByNameSearch(name);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void testGetEmployeeById_Success() {
    UUID id = UUID.randomUUID();
    EmployeeResponseDto employee = EmployeeResponseDto.builder()
        .id(id)
        .name("abc")
        .salary(50000)
        .age(30)
        .title("Developer")
        .build();
    Mockito.when(employeeService.getEmployeeById(id.toString())).thenReturn(employee);

    ResponseEntity<EmployeeResponseDto> response = employeeController.getEmployeeById(id.toString());

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("abc", response.getBody().getName());
  }

  @Test
  void testGetEmployeeById_NotFound() {
    String id = "1";
    Mockito.when(employeeService.getEmployeeById(id)).thenReturn(null);

    ResponseEntity<EmployeeResponseDto> response = employeeController.getEmployeeById(id);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
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
  void testGetHighestSalary_NoContent() {
    int highestSalary = 0;
    Mockito.when(employeeService.getHighestSalaryOfEmployees()).thenReturn(highestSalary);

    ResponseEntity<Integer> response = employeeController.getHighestSalaryOfEmployees();

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
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
  void testGetTopTenHighestEarningEmployeeNames_NoContent() {
    Mockito.when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(List.of());

    ResponseEntity<List<String>> response = employeeController.getTopTenHighestEarningEmployeeNames();

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  @Test
  void testCreateEmployee_Success() {
    CreateEmployeeRequestDto createEmployeeRequestDto = CreateEmployeeRequestDto.builder()
        .name("abc")
        .salary(50000)
        .age(30)
        .title("Developer")
        .build();
    EmployeeResponseDto createdEmployee = EmployeeResponseDto.builder()
        .id(UUID.randomUUID())
        .name("abc")
        .salary(50000)
        .age(30)
        .title("Developer")
        .build();

    Mockito.when(employeeService.createEmployee(createEmployeeRequestDto)).thenReturn(createdEmployee);

    ResponseEntity<EmployeeResponseDto> response = employeeController.createEmployee(createEmployeeRequestDto);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("abc", response.getBody().getName());
  }

  @Test
  void testCreateEmployee_InternalServerError() {
    CreateEmployeeRequestDto createEmployeeRequestDto = CreateEmployeeRequestDto.builder()
        .name("abc")
        .salary(50000)
        .age(30)
        .title("Developer")
        .build();
    Mockito.when(employeeService.createEmployee(createEmployeeRequestDto)).thenThrow(new RuntimeException("Database error"));

    ResponseEntity<EmployeeResponseDto> response = employeeController.createEmployee(createEmployeeRequestDto);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  @Test
  void testDeleteEmployee_Success() {
    String id = "1";
    Mockito.when(employeeService.deleteEmployeeById(id)).thenReturn(true);

    ResponseEntity<String> response = employeeController.deleteEmployeeById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Employee deleted successfully", response.getBody());
  }

  @Test
  void testDeleteEmployee_NotFound() {
    String id = "1";
    Mockito.when(employeeService.deleteEmployeeById(id)).thenReturn(false);

    ResponseEntity<String> response = employeeController.deleteEmployeeById(id);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Employee not found", response.getBody());
  }
}
