package com.reliaquest.api.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.reliaquest.api.constants.MockedObjectsForTesting;
import com.reliaquest.api.dto.ClientDto.DeleteEmployeeResponseDto;
import com.reliaquest.api.dto.ClientDto.EmployeeResponseDtoFromServer;
import com.reliaquest.api.dto.ClientDto.GetAllEmployeeResponseDto;
import com.reliaquest.api.dto.CreateEmployeeRequestDtoApi;
import com.reliaquest.api.exception.NoDataFoundException;
import com.reliaquest.api.exception.ServiceException;
import com.reliaquest.api.service.serviceImpl.EmployeeServiceImpl;
import com.reliaquest.api.util.RestUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

  @InjectMocks
  private EmployeeServiceImpl employeeServiceImpl;

  @Mock
  private RestUtil restUtil;

  @Test
  void getAllEmployees_Success() {
    when(this.restUtil.getAllEmployees())
        .thenReturn(MockedObjectsForTesting.getAllEmployeeResponseDto());

    assertNotNull(this.employeeServiceImpl.getAllEmployees());
  }

  @Test
  void getAllEmployees_Failure() {
    GetAllEmployeeResponseDto getAllEmployeeResponseDto =
        MockedObjectsForTesting.getAllEmployeeResponseDto();
    getAllEmployeeResponseDto.setData(List.of());
    when(this.restUtil.getAllEmployees())
        .thenReturn(getAllEmployeeResponseDto);

    assertThrows(NoDataFoundException.class,
        () -> this.employeeServiceImpl.getAllEmployees());
  }

  @Test
  void getEmployeesByName_Success() {
    GetAllEmployeeResponseDto getAllEmployeeResponseDto =
        MockedObjectsForTesting.getAllEmployeeResponseDto();
    String name = "Vinod";
    getAllEmployeeResponseDto.getData().get(0).setName(name);
    when(this.restUtil.getAllEmployees())
        .thenReturn(getAllEmployeeResponseDto);

    assertNotNull(this.employeeServiceImpl
        .getEmployeesByName(name));
  }

  @Test
  void getEmployeesByName_Failure() {
    GetAllEmployeeResponseDto getAllEmployeeResponseDto =
        MockedObjectsForTesting.getAllEmployeeResponseDto();
    when(this.restUtil.getAllEmployees())
        .thenReturn(getAllEmployeeResponseDto);

    assertThrows(NoDataFoundException.class,
        () -> this.employeeServiceImpl.getEmployeesByName("Invalid Name"));
  }

  @Test
  void getEmployeeById_Success() {
    EmployeeResponseDtoFromServer employeeResponseDtoFromServer =
        MockedObjectsForTesting.getEmployeeResDtoFromServer();
    when(this.restUtil.getEmployeeById(Mockito.any()))
        .thenReturn(employeeResponseDtoFromServer);

    assertNotNull(this.employeeServiceImpl
        .getEmployeeById(UUID.randomUUID().toString()));
  }

  @Test
  void getEmployeeById_Failure() {
    EmployeeResponseDtoFromServer employeeResponseDtoFromServer =
        MockedObjectsForTesting.getEmployeeResDtoFromServer();
    employeeResponseDtoFromServer.setData(null);
    when(this.restUtil.getEmployeeById(Mockito.any()))
        .thenReturn(employeeResponseDtoFromServer);

    assertThrows(NoDataFoundException.class,
        () -> this.employeeServiceImpl
            .getEmployeeById(UUID.randomUUID().toString()));
  }

  @Test
  void getHighestSalaryOfEmployees_Success() {
    when(this.restUtil.getAllEmployees())
        .thenReturn(MockedObjectsForTesting.getAllEmployeeResponseDto());

    assertNotNull(this.employeeServiceImpl.getHighestSalaryOfEmployees());
  }

  @Test
  void getHighestSalaryOfEmployees_Failure() {
    GetAllEmployeeResponseDto employeeResponseDto =
        MockedObjectsForTesting.getAllEmployeeResponseDto();
    employeeResponseDto.setData(new ArrayList<>());
    when(this.restUtil.getAllEmployees())
        .thenReturn(employeeResponseDto);

    assertThrows(NoDataFoundException.class,
        () -> this.employeeServiceImpl.getHighestSalaryOfEmployees());
  }

  @Test
  void getTopTenHighestEarningEmployeeNames_Success() {
    when(this.restUtil.getAllEmployees())
        .thenReturn(MockedObjectsForTesting.getAllEmployeeResponseDto());

    assertNotNull(this.employeeServiceImpl.getTopTenHighestEarningEmployeeNames());
  }

  @Test
  void getTopTenHighestEarningEmployeeNames_Failure() {
    GetAllEmployeeResponseDto employeeResponseDto =
        MockedObjectsForTesting.getAllEmployeeResponseDto();
    employeeResponseDto.setData(new ArrayList<>());
    when(this.restUtil.getAllEmployees())
        .thenReturn(employeeResponseDto);

    assertThrows(NoDataFoundException.class,
        () -> this.employeeServiceImpl.getTopTenHighestEarningEmployeeNames());
  }

  @Test
  void createEmployee_Success() {
    CreateEmployeeRequestDtoApi createEmployeeRequestDtoApi =
        MockedObjectsForTesting.getCreateEmployeeRequestDto();
    when(this.restUtil.createEmployee(createEmployeeRequestDtoApi))
        .thenReturn(MockedObjectsForTesting.getEmployeeResDtoFromServer());

    assertNotNull(this.employeeServiceImpl.createEmployee(createEmployeeRequestDtoApi));
  }

  @Test
  void createEmployee_Failure() {
    CreateEmployeeRequestDtoApi createEmployeeRequestDtoApi =
        MockedObjectsForTesting.getCreateEmployeeRequestDto();

    EmployeeResponseDtoFromServer employeeResponseDtoFromServer =
        MockedObjectsForTesting.getEmployeeResDtoFromServer();
    employeeResponseDtoFromServer.setData(null);
    when(this.restUtil.createEmployee(createEmployeeRequestDtoApi))
        .thenReturn(employeeResponseDtoFromServer);

    assertThrows(ServiceException.class,
        () -> this.employeeServiceImpl.createEmployee(createEmployeeRequestDtoApi));
  }

  @Test
  void deleteEmployeeById_Success() {
    when(this.restUtil.getEmployeeById(Mockito.any()))
        .thenReturn(MockedObjectsForTesting.getEmployeeResDtoFromServer());

    when(this.restUtil.deleteEmployeeByName(Mockito.any()))
        .thenReturn(DeleteEmployeeResponseDto.builder()
            .data(true).build());

    assertNotNull(this.employeeServiceImpl.deleteEmployeeById(UUID.randomUUID().toString()));
  }

  @Test
  void deleteEmployeeById_Failure() {
    EmployeeResponseDtoFromServer employeeResponseDtoFromServer = MockedObjectsForTesting.getEmployeeResDtoFromServer();
    employeeResponseDtoFromServer.setData(null);
    when(this.restUtil.getEmployeeById(Mockito.any()))
        .thenReturn(employeeResponseDtoFromServer);

    assertThrows(NoDataFoundException.class,
        () -> this.employeeServiceImpl.deleteEmployeeById(UUID.randomUUID().toString()));
  }

  @Test
  void deleteEmployeeById_Failure2() {
    when(this.restUtil.getEmployeeById(Mockito.any()))
        .thenReturn(MockedObjectsForTesting.getEmployeeResDtoFromServer());

    when(this.restUtil.deleteEmployeeByName(Mockito.any()))
        .thenReturn(DeleteEmployeeResponseDto.builder()
            .data(false).build());

    assertThrows(ServiceException.class,
        () -> this.employeeServiceImpl.deleteEmployeeById(UUID.randomUUID().toString()));
  }
}