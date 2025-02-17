package com.reliaquest.api.constants;

import com.reliaquest.api.dto.ClientDto.EmployeeResponseDtoFromServer;
import com.reliaquest.api.dto.ClientDto.GetAllEmployeeResponseDto;
import com.reliaquest.api.dto.CreateEmployeeRequestDtoApi;
import com.reliaquest.api.dto.EmployeeResponseDtoApi;
import java.util.List;
import java.util.UUID;

public class MockedObjectsForTesting {

  public static GetAllEmployeeResponseDto getAllEmployeeResponseDto() {
    return GetAllEmployeeResponseDto.builder()
        .data(List.of(getEmployeeResponseDtoApi()))
        .status("status").build();
  }

  public static EmployeeResponseDtoApi getEmployeeResponseDtoApi() {
    return EmployeeResponseDtoApi.builder()
        .id(UUID.randomUUID())
        .name("Vinod")
        .title("SE")
        .age(23)
        .email("vinod@gmail.com")
        .salary(12345)
        .build();
  }

  public static EmployeeResponseDtoFromServer getEmployeeResDtoFromServer() {
    return EmployeeResponseDtoFromServer.builder()
        .data(getEmployeeResponseDtoApi())
        .status("success")
        .build();
  }

  public static CreateEmployeeRequestDtoApi getCreateEmployeeRequestDto() {
    return CreateEmployeeRequestDtoApi.builder()
        .age(23)
        .name("Vinod")
        .title("SE")
        .salary(1234)
        .build();
  }

}
