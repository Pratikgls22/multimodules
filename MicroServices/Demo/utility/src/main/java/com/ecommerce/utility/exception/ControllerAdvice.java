package com.ecommerce.utility.exception;

import com.ecommerce.model.dto.ApiResponse;
import com.ecommerce.model.dto.ErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    public ResponseEntity<ApiResponse> handleException(Exception e, HttpServletRequest request)
    {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorDto errorDto = new ErrorDto
                (httpStatus, new Date().getTime(), "Something went Wrong", request.getServletPath());

        ApiResponse apiResponse = new ApiResponse
                (httpStatus, e.getMessage(), errorDto);

        log.info("handleException ::"+e);;
        return ResponseEntity.status(httpStatus).body(apiResponse);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse> handleCustomException(CustomException e, HttpServletRequest request)
    {
        HttpStatus httpStatus = e.getHttpStatus();

        ErrorDto errorDto = new ErrorDto
                (httpStatus, new Date().getTime(),e.getMessage(),request.getServletPath());
        errorDto.setError(e.getMessage());
        errorDto.setMessage(httpStatus.name());

        ApiResponse apiResponse = new ApiResponse
                (httpStatus,e.getMessage(),errorDto);

        log.info("handleCustomExcepion ::"+ e);
        return ResponseEntity.status(httpStatus).body(apiResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException e,HttpServletRequest request)
    {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;

        ErrorDto errorDto = new ErrorDto
                (httpStatus,new Date().getTime(),e.getMessage(), request.getServletPath());
        errorDto.setError(e.getMessage());
        errorDto.setMessage(httpStatus.name());

        ApiResponse apiResponse = new ApiResponse
                (httpStatus,e.getMessage(),errorDto);

        log.info("handleAccessDeniedException ::"+ e);
        return ResponseEntity.status(httpStatus).body(apiResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException e,HttpServletRequest request)
    {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ErrorDto errorDto = new ErrorDto
                (httpStatus, new Date().getTime(), "Something went wrong", request.getServletPath());

        ApiResponse apiResponse = new ApiResponse
                (httpStatus,e.getMessage(),errorDto);

        log.info("handleIllegalArgumentException ::"+ e);
        return ResponseEntity.status(httpStatus).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,HttpServletRequest request)
    {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        List<String> errorMessages = new ArrayList<>();

        e.getBindingResult().getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));

        ErrorDto errorDTO = new ErrorDto
                (httpStatus, new Date().getTime(), httpStatus.name(), request.getServletPath(), errorMessages);

        ApiResponse apiResponse = new ApiResponse(httpStatus, httpStatus.name(), errorDTO);

        log.error("handleMethodArgumentNotValidException :: ", e);
        return ResponseEntity.status(httpStatus).body(apiResponse);
    }
}
