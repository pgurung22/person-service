package com.glue.person_service.repository;

import com.glue.person_service.domain.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryTest {
    @Test
    void testRepositoryInterfaceMethods() {
        // This is a placeholder test to ensure the interface is loaded and methods are present.
        // Actual DB interaction is not tested here.
        assertDoesNotThrow(() -> PersonRepository.class.getMethod("count"));
        assertDoesNotThrow(() -> PersonRepository.class.getMethod("findByNameOrAge", String.class, Integer.class, PageRequest.class));
    }
}
