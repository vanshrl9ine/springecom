package com.ecommerce.ecom.exceptions;

import com.ecommerce.ecom.payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice //intercepts any exception being thrown globally.

public class MyGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String,String> response = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((err)->{

            String fieldName =((FieldError)err).getField();
            String message =((FieldError)err).getDefaultMessage();
            response.put(fieldName,message);

        });

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFoundException e){
        String message =e.getMessage();
       APIResponse apiResponse = new APIResponse(message,false);
       return new ResponseEntity<APIResponse>(apiResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> myAPIException(APIException e){
        String message =e.getMessage();

        APIResponse apiResponse = new APIResponse(message,false);
        return new ResponseEntity<APIResponse>(apiResponse,HttpStatus.BAD_REQUEST);
    }
}
