package com.reliaquest.api.dto.ClientResponseDto;

import com.reliaquest.api.dto.EmployeeResponseDtoApi;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllEmployeeResponseDto {
  private List<EmployeeResponseDtoApi> data;
  private String status;

}
