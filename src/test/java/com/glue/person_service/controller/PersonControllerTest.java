package com.glue.person_service.controller;

import com.glue.person_service.domain.PersonDTO;
import com.glue.person_service.domain.PersonNoPasswordAndUsername;
import com.glue.person_service.service.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PersonControllerTest {
    @Mock
    private PersonServiceImpl personServiceImpl;
    @InjectMocks
    private PersonController personController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void savePerson_shouldReturnCreated() {
        PersonDTO dto = new PersonDTO();
        when(personServiceImpl.savePerson(dto)).thenReturn(null);
        ResponseEntity<String> response = personController.savePerson(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Person added successfully", response.getBody());
    }

    @Test
    void getPeople_shouldReturnList() {
        PersonDTO dto = new PersonDTO();
        Page<PersonDTO> page = new PageImpl<>(Collections.singletonList(dto));
        when(personServiceImpl.getPeople(any(PageRequest.class))).thenReturn(page);
        List<PersonDTO> result = personController.getPeople(null, null);
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void getPeopleByName_shouldReturnPeople() {
        List<PersonNoPasswordAndUsername> people = Arrays.asList(
    new PersonNoPasswordAndUsername(1L, "A", "B", "a@b.com", "123", "2000-01-01", 25),
    new PersonNoPasswordAndUsername(2L, "C", "D", "c@d.com", "456", "1990-01-01", 30)
);
        when(personServiceImpl.getPeopleByNameOrAge(anyString(), any(), any())).thenReturn(people);
        ResponseEntity<?> response = personController.getPeopleByName(null, null, "John", 30);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(people, response.getBody());
    }

    @Test
    void getPeopleByName_shouldReturnNotFound() {
        when(personServiceImpl.getPeopleByNameOrAge(anyString(), any(), any())).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = personController.getPeopleByName(null, null, "John", 30);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No person matches the filter conditions", response.getBody());
    }

    @Test
    void deletePersonById_shouldReturnOk() {
        when(personServiceImpl.deletePersonById(1L)).thenReturn(true);
        ResponseEntity<String> response = personController.deletePersonById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Person removed successfully", response.getBody());
    }

    @Test
    void deletePersonById_shouldReturnNotFound() {
        when(personServiceImpl.deletePersonById(1L)).thenReturn(false);
        ResponseEntity<String> response = personController.deletePersonById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Person with indicated ID does not exist", response.getBody());
    }

    @Test
    void updatePersonById_shouldReturnCreated() {
        PersonDTO dto = new PersonDTO();
        when(personServiceImpl.updatePersonById(1L, dto)).thenReturn(dto);
        ResponseEntity<String> response = personController.updatePersonById(1L, dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Person updated successfully", response.getBody());
    }

    @Test
    void getPersonById_shouldReturnCreated() {
        PersonDTO dto = new PersonDTO();
        when(personServiceImpl.getPersonById(1L)).thenReturn(dto);
        ResponseEntity<PersonDTO> response = personController.getPersonById(1L);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }
}
