package com.reliaquest.api.constants;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Status {

  SUCCESS("Successfully processed request."),
  FAILED("Failed to process request.");

  @JsonValue
  private final String value;

  Status(String value) {
    this.value = value;
  }
}
