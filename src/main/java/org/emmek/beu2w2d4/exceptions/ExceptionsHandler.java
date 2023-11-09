package org.emmek.beu2w2d4.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorPayload handleNotFound(NotFoundException e) {
        return new ErrorPayload(e.getMessage(), new Date());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorPayload handleBadRequest(BadRequestException e) {
        return new ErrorPayload(e.getMessage(), new Date());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorPayload handleIllegalArgument(IllegalArgumentException e) {
        return new ErrorPayload("Argument not Valid!", new Date());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorPayload handleGeneric(Exception e) {
        log.error("Server Error: NERV!", e);
        return new ErrorPayload("Server Error: NERV!", new Date());
    }

}
