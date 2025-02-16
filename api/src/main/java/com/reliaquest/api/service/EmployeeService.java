package com.reliaquest.api.service;

import com.reliaquest.api.dto.CreateEmployeeRequestDto;
import com.reliaquest.api.dto.EmployeeResponseDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {

  List<EmployeeResponseDto> getAllEmployees();

  List<EmployeeResponseDto> getEmployeesByName(String name);

  EmployeeResponseDto getEmployeeById(String id);

  Integer getHighestSalaryOfEmployees();

  List<String> getTopTenHighestEarningEmployeeNames();

  EmployeeResponseDto createEmployee(CreateEmployeeRequestDto createEmployeeRequestDto);

  boolean deleteEmployeeById(String id);

}
