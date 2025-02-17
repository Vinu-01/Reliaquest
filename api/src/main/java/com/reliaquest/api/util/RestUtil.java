package com.reliaquest.api.util;

import com.reliaquest.api.config.AppConfig;
import com.reliaquest.api.dto.ClientDto.DeleteEmployeeRequestDto;
import com.reliaquest.api.dto.ClientDto.DeleteEmployeeResponseDto;
import com.reliaquest.api.dto.ClientDto.EmployeeResponseDtoFromServer;
import com.reliaquest.api.dto.ClientDto.GetAllEmployeeResponseDto;
import com.reliaquest.api.dto.CreateEmployeeRequestDtoApi;
import com.reliaquest.api.exception.NoDataFoundException;
import com.reliaquest.api.exception.ServiceException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class RestUtil {

  private final WebClient webClient;

  private final AppConfig appConfig;

  @SneakyThrows
  public GetAllEmployeeResponseDto getAllEmployees() {
    String url = appConfig.getEmployeeServiceBaseUrl() + appConfig.getEmployeeServiceResourceUrl();
    log.info("Calling employee service at {} to get all employees", url);

    ResponseEntity<GetAllEmployeeResponseDto> response = webClient.get().uri(url)
        .exchangeToMono(clientResponse -> clientResponse.toEntity(GetAllEmployeeResponseDto.class))
        .block();

    if (response == null) {
      log.error("Error occurred while fetching All employees data. Response is null");
      throw new ServiceException(
          "Error occurred while fetching All employees data. Received Null response");
    }

    HttpStatus status = HttpStatus.valueOf(response.getStatusCode().value());
    switch (status) {
      case OK:
        GetAllEmployeeResponseDto getAllEmployeesResponseDto = response.getBody();
        log.info("Fetched all employees successfully. Total number of employees fetched : {}",
            getAllEmployeesResponseDto.getData().size());
        return getAllEmployeesResponseDto;

      default:
        log.error("Error occurred while fetching All employees data. Status code returned: {}",
            status);
        throw new ServiceException(
            "Error occurred while fetching All employees data. " + "Status code returned: "
                + status);
    }
  }

  @SneakyThrows
  public EmployeeResponseDtoFromServer getEmployeeById(UUID id) {
    String url =
        appConfig.getEmployeeServiceBaseUrl() + appConfig.getEmployeeServiceResourceUrl() + "/"
            + id;

    log.info("Calling employee service at {} to get employee with id : {}", url, id);

    ResponseEntity<EmployeeResponseDtoFromServer> response = webClient.get().uri(url)
        .exchangeToMono(clientResponse -> clientResponse.toEntity(EmployeeResponseDtoFromServer.class))
        .block();

    if (response == null) {
      log.error("Error occurred while fetching employee data with id: {}, Response is null", id);
      throw new NoDataFoundException("Error occurred while fetching employee data with id : " + id
          + ", Received Null response");
    }

    HttpStatus status = HttpStatus.valueOf(response.getStatusCode().value());
    switch (status) {
      case OK:
        EmployeeResponseDtoFromServer getEmployeeResponseDtoFromServer = response.getBody();
        log.info("Successfully fetched employee data with id : {}", id);
        return getEmployeeResponseDtoFromServer;

      case NOT_FOUND:
        throw new NoDataFoundException("Employee with ID : " + id + " not found.");

      case INTERNAL_SERVER_ERROR:
      default:
        throw new ServiceException(
            "Error occurred while fetching employees data with id. " + "Status code returned: "
                + status);
    }
  }

  @SneakyThrows
  public DeleteEmployeeResponseDto deleteEmployeeByName(String name) {
    String url = appConfig.getEmployeeServiceBaseUrl() + appConfig.getEmployeeServiceResourceUrl();

    log.info("Calling employee service at {} to delete employee with name : {}", url, name);

    DeleteEmployeeRequestDto deleteEmployeeRequestDto = new DeleteEmployeeRequestDto(name);

    ResponseEntity<DeleteEmployeeResponseDto> response = webClient.method(HttpMethod.DELETE)
        .uri(url).body(Mono.just(deleteEmployeeRequestDto), DeleteEmployeeRequestDto.class)
        .exchangeToMono(clientResponse -> clientResponse.toEntity(DeleteEmployeeResponseDto.class))
        .block();

    HttpStatus status = HttpStatus.valueOf(response.getStatusCode().value());
    switch (status) {
      case OK:
        return response.getBody();

      default:
        log.error("Error occurred while deleting the employee. Status code returned: {}", status);
        throw new ServiceException("Error occurred while deleting the employees with name : " + name
            + ", Status code returned: " + status);
    }
  }

  @SneakyThrows
  public EmployeeResponseDtoFromServer createEmployee(
      CreateEmployeeRequestDtoApi createEmployeeRequestDtoApi) {
    String url = appConfig.getEmployeeServiceBaseUrl() + appConfig.getEmployeeServiceResourceUrl();

    log.info("Calling employee service at {} to create employee with name : {}", url,
        createEmployeeRequestDtoApi.getName());

    ResponseEntity<EmployeeResponseDtoFromServer> response = webClient.post().uri(url)
        .body(Mono.just(createEmployeeRequestDtoApi), CreateEmployeeRequestDtoApi.class)
        .exchangeToMono(clientResponse -> clientResponse.toEntity(EmployeeResponseDtoFromServer.class))
        .block();

    HttpStatus status = HttpStatus.valueOf(response.getStatusCode().value());
    switch (status) {
      case OK:
        return response.getBody();

      default:
        log.error("Error occurred while creating the employee. Status code returned: {}", status);
        throw new ServiceException("Error occurred while creating the employees with name : "
            + createEmployeeRequestDtoApi.getName() + ", Status code returned: " + status);
    }
  }
}
