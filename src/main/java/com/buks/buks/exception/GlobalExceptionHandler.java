package com.buks.buks.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        ErrorCode ec = ex.getErrorCode();

        ErrorResponse error = new ErrorResponse(
                ec.getStatus().value(),
                ec.getCode(),
                ec.getMessage(),
                null,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, ec.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidExceptions(MethodArgumentNotValidException ex) {
        String details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ErrorCode ec = ErrorCode.VALIDATION_ERROR;

        ErrorResponse error = new ErrorResponse(
                ec.getStatus().value(),
                ec.getCode(),
                ec.getMessage(),
                details,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, ec.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        ErrorCode ec = ErrorCode.INTERNAL_ERROR;
        String safeDetails = (ex.getMessage() != null) ? ex.getMessage() : ec.getMessage();

        ErrorResponse error = new ErrorResponse(
                ec.getStatus().value(),
                ec.getCode(),
                safeDetails,
                null,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, ec.getStatus());
    }
}
