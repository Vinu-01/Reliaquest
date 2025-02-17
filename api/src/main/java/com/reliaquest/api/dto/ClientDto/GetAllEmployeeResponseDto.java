package com.reliaquest.api.dto.ClientDto;

import com.reliaquest.api.dto.EmployeeResponseDtoApi;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GetAllEmployeeResponseDto {
  private List<EmployeeResponseDtoApi> data;
  private String status;

}
