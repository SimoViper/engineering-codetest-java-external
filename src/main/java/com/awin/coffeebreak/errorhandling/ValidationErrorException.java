package com.awin.coffeebreak.errorhandling;

import com.awin.coffeebreak.dto.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationErrorException extends Exception{

    private ErrorResponse validationError;

}
