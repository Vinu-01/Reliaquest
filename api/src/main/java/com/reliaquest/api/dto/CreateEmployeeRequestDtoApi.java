package com.reliaquest.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CreateEmployeeRequestDtoApi {

  @NotBlank(message = "Name cannot be blank")
  private final String name;

  @Min(value = 1, message = "Salary should be greater than 0")
  @NotNull(message = "Salary cannot be null")
  private final Integer salary;

  @Min(value = 16, message = "Age should be between 16 and 75")
  @Max(value = 75, message = "Age should be between 16 and 75")
  @NotNull(message = "Age cannot be null")
  private final Integer age;

  @NotBlank(message = "Title cannot be blank")
  private final String title;

  public EmployeeResponseDtoApi fromCreateEmployeeRequestDto() {
    UUID uuid = UUID.randomUUID();
    String email = name + "@" + "email.com";

    return EmployeeResponseDtoApi.builder()
        .id(uuid)
        .name(name)
        .salary(salary)
        .age(age)
        .title(title)
        .email(email)
        .build();
  }
}
