package com.glue.person_service.exception;

public class PersonDoesNotExistException extends RuntimeException{

    public PersonDoesNotExistException(String message){
        super(message);
    }

}
