package com.reliaquest.api.dto.ClientDto;

import com.reliaquest.api.dto.EmployeeResponseDtoApi;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EmployeeResponseDtoFromServer {
  private EmployeeResponseDtoApi data;
  private String status;
}
