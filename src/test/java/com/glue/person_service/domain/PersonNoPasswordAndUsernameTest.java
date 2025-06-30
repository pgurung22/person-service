package com.glue.person_service.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonNoPasswordAndUsernameTest {
    @Test
    void testBuilderAndGettersSetters() {
        PersonNoPasswordAndUsername person = PersonNoPasswordAndUsername.builder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .phone("1234567890")
                .dateOfBirth("2000-01-01")
                .age(25)
                .build();
        assertEquals(1L, person.getId());
        assertEquals("John", person.getName());
        assertEquals("Doe", person.getSurname());
        assertEquals("john@example.com", person.getEmail());
        assertEquals("1234567890", person.getPhone());
        assertEquals("2000-01-01", person.getDateOfBirth());
        assertEquals(25, person.getAge());
    }

    @Test
    void testAllArgsConstructor() {
        PersonNoPasswordAndUsername person = new PersonNoPasswordAndUsername(1L, "John", "Doe", "john@example.com", "1234567890", "2000-01-01", 25);
        assertEquals("John", person.getName());
    }
}
