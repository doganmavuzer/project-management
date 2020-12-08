package com.project.management.exception;

import com.project.management.user.exception.UserAlreadyExist;
import com.project.management.user.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class CustomResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleAllUserNotFoundException(Exception ex, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExist.class)
    public final ResponseEntity<Object> handleAllUserAlreadExistException(Exception ex, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        List<ExceptionResponse> exceptionResponses = errors.stream()
                .map(error -> new ExceptionResponse(LocalDateTime.now(), error.getDefaultMessage(), error.getField())).collect(Collectors.toList());

        return new ResponseEntity<>(exceptionResponses, HttpStatus.BAD_REQUEST);
    }
}
