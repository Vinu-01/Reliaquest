package com.reliaquest.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResponseDtoApi {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("employee_name")
  private String name;

  @JsonProperty("employee_salary")
  private Integer salary;

  @JsonProperty("employee_age")
  private Integer age;

  @JsonProperty("employee_title")
  private String title;

  @JsonProperty("employee_email")
  private String email;

}
