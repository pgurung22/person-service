package com.glue.person_service.domain.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    @Test
    void testBuilderAndGettersSetters() {
        Person person = Person.builder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .phone("1234567890")
                .dateOfBirth("2000-01-01")
                .age(25)
                .username("johndoe")
                .password("password")
                .build();
        assertEquals(1L, person.getId());
        assertEquals("John", person.getName());
        assertEquals("Doe", person.getSurname());
        assertEquals("john@example.com", person.getEmail());
        assertEquals("1234567890", person.getPhone());
        assertEquals("2000-01-01", person.getDateOfBirth());
        assertEquals(25, person.getAge());
        assertEquals("johndoe", person.getUsername());
        assertEquals("password", person.getPassword());
    }

    @Test
    void testNoArgsConstructor() {
        Person person = new Person();
        assertNull(person.getName());
    }
}
