package com.reliaquest.api.controller;

import com.reliaquest.api.dto.Response;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Written this controller to get status field in response as well
 * Since it was mentioned that the controller shouldn't be changes, but we cannot add status without adding wrapper to the return type in response
 * @param <Entity>
 * @param <Input>
 */
public interface EmployeeControllerExpected<Entity, Input> {

  @GetMapping()
  ResponseEntity<Response<List<Entity>>> getAllEmployees();

  @GetMapping("/search/{searchString}")
  ResponseEntity<Response<List<Entity>>> getEmployeesByNameSearch(@PathVariable String searchString);

  @GetMapping("/{id}")
  ResponseEntity<Response<Entity>> getEmployeeById(@PathVariable String id);

  @GetMapping("/highestSalary")
  ResponseEntity<Response<Integer>> getHighestSalaryOfEmployees();

  @GetMapping("/topTenHighestEarningEmployeeNames")
  ResponseEntity<Response<List<String>>> getTopTenHighestEarningEmployeeNames();

  @PostMapping()
  ResponseEntity<Response<Entity>> createEmployee(@RequestBody Input employeeInput);

  @DeleteMapping("/{id}")
  ResponseEntity<Response<String>> deleteEmployeeById(@PathVariable String id);
}
