package com.reliaquest.api.dto.ClientResponseDto;

import com.reliaquest.api.dto.EmployeeResponseDtoApi;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeResponseDto {
  private EmployeeResponseDtoApi data;
  private String status;
}
