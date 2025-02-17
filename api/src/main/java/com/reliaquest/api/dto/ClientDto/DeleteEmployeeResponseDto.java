package com.reliaquest.api.dto.ClientDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DeleteEmployeeResponseDto {
  private Boolean data;
  private String status;
}
