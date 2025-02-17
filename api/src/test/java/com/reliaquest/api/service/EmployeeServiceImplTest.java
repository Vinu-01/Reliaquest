package com.reliaquest.api.service;

import com.reliaquest.api.dao.EmployeeDao;
import com.reliaquest.api.dto.CreateEmployeeRequestDtoApi;
import com.reliaquest.api.dto.EmployeeResponseDtoApi;
import com.reliaquest.api.exception.NoDataFoundException;
import com.reliaquest.api.exception.ServiceException;
import com.reliaquest.api.service.serviceImpl.EmployeeServiceImpl;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

  @Mock
  private EmployeeDao employeeDao;

  @InjectMocks
  private EmployeeServiceImpl employeeService;

  private EmployeeResponseDtoApi employeeResponseDtoApi;

  @BeforeEach
  void setUp() {
    employeeResponseDtoApi = EmployeeResponseDtoApi.builder()
        .id(UUID.randomUUID())
        .name("abc")
        .salary(50000)
        .age(30)
        .title("Developer")
        .build();
  }

  @Test
  void testGetAllEmployees_Success() {
    List<EmployeeResponseDtoApi> employees = List.of(employeeResponseDtoApi);
    Mockito.when(employeeDao.getAllEmployees()).thenReturn(employees);

    List<EmployeeResponseDtoApi> result = employeeService.getAllEmployees();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("abc", result.get(0).getName());
  }

  @Test
  void testGetAllEmployees_EmptyList() {
    Mockito.when(employeeDao.getAllEmployees()).thenReturn(List.of());

    assertThrows(NoDataFoundException.class,
        () -> employeeService.getAllEmployees());
  }

  @Test
  void testGetEmployeesByName_Success() {
    String name = "abc";
    List<EmployeeResponseDtoApi> employees = List.of(employeeResponseDtoApi);
    Mockito.when(employeeDao.getEmployeesByName(name)).thenReturn(employees);

    List<EmployeeResponseDtoApi> result = employeeService.getEmployeesByName(name);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("abc", result.get(0).getName());
  }

  @Test
  void testGetEmployeesByName_NotFound() {
    String name = "xyz";
    Mockito.when(employeeDao.getEmployeesByName(name)).thenReturn(List.of());

    assertThrows(NoDataFoundException.class,
        () -> employeeService.getEmployeesByName(name));
  }

  @Test
  void testGetEmployeeById_Success() {
    String id = "1";
    Mockito.when(employeeDao.getEmployeeById(id)).thenReturn(employeeResponseDtoApi);

    EmployeeResponseDtoApi result = employeeService.getEmployeeById(id);

    assertNotNull(result);
    assertEquals("abc", result.getName());
  }

  @Test
  void testGetEmployeeById_NotFound() {
    String id = "1";
    Mockito.when(employeeDao.getEmployeeById(id)).thenReturn(null);

    assertThrows(NoDataFoundException.class,
        () -> employeeService.getEmployeeById(id));
  }

  @Test
  void testGetHighestSalary_Success() {
    Integer highestSalary = 100000;
    Mockito.when(employeeDao.getHighestSalaryOfEmployees()).thenReturn(highestSalary);

    Integer result = employeeService.getHighestSalaryOfEmployees();

    assertNotNull(result);
    assertEquals(100000, result);
  }

  @Test
  void testGetHighestSalary_NoData() {
    Mockito.when(employeeDao.getHighestSalaryOfEmployees()).thenReturn(0);

    assertThrows(NoDataFoundException.class,
        () -> employeeService.getHighestSalaryOfEmployees());
  }

  @Test
  void testGetTopTenHighestEarningEmployeeNames_Success() {
    List<String> topEarningNames = List.of("abc", "xyz");
    Mockito.when(employeeDao.getTopTenHighestEarningEmployeeNames()).thenReturn(topEarningNames);

    List<String> result = employeeService.getTopTenHighestEarningEmployeeNames();

    assertNotNull(result);
    assertEquals(2, result.size());
    assertTrue(result.contains("abc"));
  }

  @Test
  void testGetTopTenHighestEarningEmployeeNames_NoData() {
    Mockito.when(employeeDao.getTopTenHighestEarningEmployeeNames()).thenReturn(List.of());

    assertThrows(NoDataFoundException.class,
        () -> employeeService.getTopTenHighestEarningEmployeeNames());
  }

  @Test
  void testCreateEmployee_Success() {
    CreateEmployeeRequestDtoApi createEmployeeRequestDtoApi = CreateEmployeeRequestDtoApi.builder()
        .name("abc")
        .salary(50000)
        .age(30)
        .title("Developer")
        .build();
    Mockito.when(employeeDao.createEmployee(createEmployeeRequestDtoApi)).thenReturn(
        employeeResponseDtoApi);

    EmployeeResponseDtoApi result = employeeService.createEmployee(createEmployeeRequestDtoApi);

    assertNotNull(result);
    assertEquals("abc", result.getName());
  }

  @Test
  void testCreateEmployee_Failure() {
    CreateEmployeeRequestDtoApi createEmployeeRequestDtoApi = CreateEmployeeRequestDtoApi.builder()
        .name("abc")
        .salary(50000)
        .age(30)
        .title("Developer")
        .build();
    Mockito.when(employeeDao.createEmployee(createEmployeeRequestDtoApi)).thenReturn(null);

    assertThrows(ServiceException.class,
        () -> employeeService.createEmployee(createEmployeeRequestDtoApi));
  }

  @Test
  void testDeleteEmployee_Success() {
    String id = "1";
    Mockito.when(employeeDao.deleteEmployeeById(id)).thenReturn(true);

    boolean result = employeeService.deleteEmployeeById(id);

    assertTrue(result);
  }

  @Test
  void testDeleteEmployee_Failure() {
    String id = "1";
    Mockito.when(employeeDao.deleteEmployeeById(id)).thenReturn(false);

    assertThrows(ServiceException.class,
        () -> employeeService.deleteEmployeeById(id));
  }
}
