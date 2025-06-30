package com.glue.person_service.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonDTOTest {
    @Test
    void testBuilderAndGettersSetters() {
        PersonDTO dto = PersonDTO.builder()
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
        assertEquals(1L, dto.getId());
        assertEquals("John", dto.getName());
        assertEquals("Doe", dto.getSurname());
        assertEquals("john@example.com", dto.getEmail());
        assertEquals("1234567890", dto.getPhone());
        assertEquals("2000-01-01", dto.getDateOfBirth());
        assertEquals(25, dto.getAge());
        assertEquals("johndoe", dto.getUsername());
        assertEquals("password", dto.getPassword());
    }

    @Test
    void testNoArgsConstructor() {
        PersonDTO dto = new PersonDTO();
        assertNull(dto.getName());
    }
}
