package com.reliaquest.api.model;

import com.reliaquest.api.dto.EmployeeResponseDto;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {

  private UUID id;
  private String name;
  private Integer salary;
  private Integer age;
  private String title;
  private String email;

  public EmployeeResponseDto toEmployeeResponseDto() {
    return EmployeeResponseDto.builder()
        .id(id)
        .name(name)
        .salary(salary)
        .age(age)
        .title(title)
        .email(email)
        .build();
  }

}
