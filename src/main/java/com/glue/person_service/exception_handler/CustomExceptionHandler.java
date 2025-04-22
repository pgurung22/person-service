package com.glue.person_service.exception_handler;
import com.glue.person_service.exception.PersonDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Map<String, String> handleMissingRequestParameter(MissingServletRequestParameterException exception) {
        log.info(exception.getClass() + "has been thrown");
     return constructErrorResponse(exception);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidFields(MethodArgumentNotValidException exception) {
        log.info(exception.getClass() + "has been thrown");
        Map<String, String> map =  new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> map.put("errorMessage", fieldError.getDefaultMessage()));
        return map;
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> processRuntimeException(RuntimeException exception) {
        return constructErrorResponse(exception);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PersonDoesNotExistException.class)
    public Map<String, String> handlePersonDoesNotExistException(PersonDoesNotExistException exception) {
        log.info(exception.getClass() + "has been thrown");
        return constructErrorResponse(exception);
    }
    private Map<String, String> constructErrorResponse(Exception exception){
        Map<String, String> map =  new HashMap<>();
        map.put("errorMessage", exception.getMessage());
        return map;
    }

}
