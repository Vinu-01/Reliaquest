package com.reliaquest.api.service;

import com.reliaquest.api.dto.CreateEmployeeRequestDtoApi;
import com.reliaquest.api.dto.EmployeeResponseDtoApi;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {

  List<EmployeeResponseDtoApi> getAllEmployees();

  List<EmployeeResponseDtoApi> getEmployeesByName(String name);

  EmployeeResponseDtoApi getEmployeeById(String id);

  Integer getHighestSalaryOfEmployees();

  List<String> getTopTenHighestEarningEmployeeNames();

  EmployeeResponseDtoApi createEmployee(CreateEmployeeRequestDtoApi createEmployeeRequestDtoApi);

  boolean deleteEmployeeById(String id);

}
