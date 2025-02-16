package com.reliaquest.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.reliaquest.api.constants.Status;

@JsonInclude(Include.NON_NULL)
public record Response<T>(T data, Status status, String error) {

  public static <T> Response<T> handle() {
    return new Response<>(null, Status.SUCCESS, null);
  }

  public static <T> Response<T> handleWithData(T data) {
    return new Response<>(data, Status.SUCCESS, null);
  }

  public static <T> Response<T> handleForError(String error) {
    return new Response<>(null, Status.FAILED, error);
  }
}
