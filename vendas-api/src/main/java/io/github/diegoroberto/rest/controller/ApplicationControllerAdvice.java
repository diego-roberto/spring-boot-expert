package io.github.diegoroberto.rest.controller;

import io.github.diegoroberto.exception.BusinessException;
import io.github.diegoroberto.exception.NoResultsException;
import io.github.diegoroberto.exception.OrderNotFoundException;
import io.github.diegoroberto.util.ApiErrors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBusinessException(BusinessException ex){
        String msg = ex.getMessage();
        return new ApiErrors(msg);
    }

    @ExceptionHandler(NoResultsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleNoResultsException(NoResultsException ex){
        String msg = ex.getMessage();
        return new ApiErrors(msg);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleOrderNotFoundException(OrderNotFoundException ex){
        String msg = ex.getMessage();
        return new ApiErrors(msg);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ApiErrors(errors);
    }
}
