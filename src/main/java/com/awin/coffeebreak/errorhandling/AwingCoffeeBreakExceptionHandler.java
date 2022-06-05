package com.awin.coffeebreak.errorhandling;

import com.awin.coffeebreak.dto.ErrorCode;
import com.awin.coffeebreak.dto.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.xml.bind.JAXBException;

@Slf4j
@ControllerAdvice
public class AwingCoffeeBreakExceptionHandler {

    private static final String UNKNOWN_ERROR_OCCURRED = "Unknown error occurred";
    private static final String PARSING_ERROR_OCCURRED = "Parsing error occurred";
    private static final String STAFF_MEMBER_NOT_FOUND = "Staff Member not found";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleAllUncaughtException(
            Exception exception){
        log.error(UNKNOWN_ERROR_OCCURRED, exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .errorCode(ErrorCode.GENERIC_ERROR)
                        .build());
    }

    @ExceptionHandler(value
            = {JsonProcessingException.class, JAXBException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleAllParsingExceptions(
            Exception exception){
        log.error(PARSING_ERROR_OCCURRED, exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .errorCode(ErrorCode.PARSING_ERROR)
                        .errorMessage(PARSING_ERROR_OCCURRED)
                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleMemberNotFound(
            Exception exception){
        log.error(STAFF_MEMBER_NOT_FOUND, exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .errorCode(ErrorCode.MEMBER_NOT_FOUND)
                        .errorMessage(STAFF_MEMBER_NOT_FOUND)
                        .build());
    }

    @ExceptionHandler(ValidationErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            ValidationErrorException exception){
        log.error(exception.getValidationError().getErrorMessage(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getValidationError());
    }
}
