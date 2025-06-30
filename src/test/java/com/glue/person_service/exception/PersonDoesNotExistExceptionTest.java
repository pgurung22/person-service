package com.glue.person_service.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonDoesNotExistExceptionTest {
    @Test
    void testMessage() {
        String msg = "Person not found!";
        PersonDoesNotExistException ex = new PersonDoesNotExistException(msg);
        assertEquals(msg, ex.getMessage());
    }
}
