package com.ecommerce.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class  ErrorDto {
    private Long timestamp;
    private int status;
    private Object error;
    private String message;
    private String path;
    @JsonIgnore
    private HttpStatus httpStatus;

    public ErrorDto(HttpStatus httpStatus, long timestamp, String message, String path) {
        this.timestamp = timestamp;
        this.status = httpStatus.value();
        this.error = httpStatus.name();
        this.message = message;
        this.path = path;
        this.httpStatus = httpStatus;
    }

    public ErrorDto(HttpStatus httpStatus, long timestamp, String message, String path, List<String> error) {
        this.timestamp = timestamp;
        this.httpStatus = httpStatus;
        this.status = httpStatus.value();
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
