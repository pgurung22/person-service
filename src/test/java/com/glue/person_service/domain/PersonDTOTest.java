package com.glue.person_service.domain;

import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PersonDTOTest {
    private final Validator validator;

    public PersonDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validPersonDTO_shouldHaveNoViolations() {
        PersonDTO dto = PersonDTO.builder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email("john@doe.com")
                .phone("1234567890")
                .dateOfBirth("2000-01-01")
                .age(25)
                .username("johndoe")
                .password("Abcdef1*")
                .build();
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidEmail_shouldHaveViolation() {
        PersonDTO dto = PersonDTO.builder().email("not-an-email").age(25).username("johndoe").password("Abcdef1*").build();
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Invalid email format")));
    }

    @Test
    void underage_shouldHaveViolation() {
        PersonDTO dto = PersonDTO.builder().age(17).username("johndoe").password("Abcdef1*").build();
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("minimum 18 years old")));
    }

    @Test
    void invalidUsername_shouldHaveViolation() {
        PersonDTO dto = PersonDTO.builder().age(25).username("1bad").password("Abcdef1*").build();
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("username does not match")));
    }

    @Test
    void invalidPassword_shouldHaveViolation() {
        PersonDTO dto = PersonDTO.builder().age(25).username("johndoe").password("bad").build();
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("password does not match")));
    }
}
