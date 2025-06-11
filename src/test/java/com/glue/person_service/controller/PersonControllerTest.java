package com.glue.person_service.controller;

import com.glue.person_service.domain.PersonDTO;
import com.glue.person_service.domain.PersonNoPasswordAndUsername;
import com.glue.person_service.service.PersonServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PersonControllerTest {
    @Mock
    private PersonServiceImpl personServiceImpl;
    @InjectMocks
    private PersonController personController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void savePerson_shouldReturnCreated() throws Exception {
        PersonDTO dto = new PersonDTO();
        dto.setAge(18);
        when(personServiceImpl.savePerson(org.mockito.ArgumentMatchers.any(PersonDTO.class))).thenReturn(null);
        mockMvc.perform(post("/api/v1/person/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Person added successfully"));
    }

    @Test
    void getPeople_shouldReturnList() throws Exception {
        PersonDTO dto = new PersonDTO();
        Page<PersonDTO> page = new PageImpl<>(Collections.singletonList(dto));
        when(personServiceImpl.getPeople(org.mockito.ArgumentMatchers.any(PageRequest.class))).thenReturn(page);
        mockMvc.perform(get("/api/v1/getPeople"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getPeopleByName_shouldReturnList() throws Exception {
        List<PersonNoPasswordAndUsername> people = Arrays.asList(
                new PersonNoPasswordAndUsername(1L, "John", "Doe", "john@example.com", "1234567890", "1990-01-01", 30)
        );
        when(personServiceImpl.getPeopleByNameOrAge(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(PageRequest.class))).thenReturn(people);
        mockMvc.perform(get("/api/v1/filter")
                .param("name", "John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getPeopleByName_shouldReturnNotFound() throws Exception {
        when(personServiceImpl.getPeopleByNameOrAge(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(PageRequest.class))).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/v1/filter")
                .param("name", "John"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No person matches the filter conditions"));
    }

    @Test
    void deletePersonById_shouldReturnOk() throws Exception {
        when(personServiceImpl.deletePersonById(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/v1/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Person removed successfully"));
    }

    @Test
    void deletePersonById_shouldReturnNotFound() throws Exception {
        when(personServiceImpl.deletePersonById(1L)).thenReturn(false);
        mockMvc.perform(delete("/api/v1/delete/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Person with indicated ID does not exist"));
    }

    @Test
    void updatePersonById_shouldReturnCreated() throws Exception {
        PersonDTO dto = new PersonDTO();
        when(personServiceImpl.updatePersonById(eq(1L), org.mockito.ArgumentMatchers.any(PersonDTO.class))).thenReturn(dto);
        mockMvc.perform(put("/api/v1/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Person updated successfully"));
    }

    @Test
    void getPersonById_shouldReturnCreated() throws Exception {
        PersonDTO dto = new PersonDTO();
        when(personServiceImpl.getPersonById(1L)).thenReturn(dto);
        mockMvc.perform(get("/api/v1/get/1"))
                .andExpect(status().isCreated());
    }
}
