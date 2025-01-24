package com.job.females.in.tech.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * <h1>CustomException</h1>
 * <p>
 * This class will be used for handling Custom exception
 * </p>
 */

public class CustomException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  private final String message;
  @Getter
  private final HttpStatus httpStatus;

  /**
   * <p>
   * This Method handles CustomException.
   * </p>
   *
   */
  public CustomException(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }

  @Override
  public String getMessage() {
    return message;
  }

}