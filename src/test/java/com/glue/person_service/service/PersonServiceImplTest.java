package com.glue.person_service.service;

import com.glue.person_service.domain.PersonDTO;
import com.glue.person_service.domain.PersonNoPasswordAndUsername;
import com.glue.person_service.domain.entity.Person;
import com.glue.person_service.exception.PersonDoesNotExistException;
import com.glue.person_service.mapper.PersonMapper;
import com.glue.person_service.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PersonServiceImplTest {
    @Mock
    private PersonRepository personRepository;
    @Mock
    private PersonMapper personMapper;
    @InjectMocks
    private PersonServiceImpl personServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void savePerson_shouldSaveAndReturnPerson() {
        PersonDTO dto = new PersonDTO();
        Person person = new Person();
        when(personMapper.transformPersonDTOtoPerson(dto)).thenReturn(person);
        when(personRepository.save(person)).thenReturn(person);
        Person result = personServiceImpl.savePerson(dto);
        assertEquals(person, result);
        verify(personRepository).save(person);
    }

    @Test
    void getPeople_shouldReturnPageOfPersonDTO() {
        Person person = new Person();
        PersonDTO dto = new PersonDTO();
        Page<Person> personPage = new PageImpl<>(Collections.singletonList(person));
        when(personRepository.findAll(any(PageRequest.class))).thenReturn(personPage);
        when(personMapper.transformPersonToPersonDTO(person)).thenReturn(dto);
        Page<PersonDTO> result = personServiceImpl.getPeople(PageRequest.of(0, 10));
        assertEquals(1, result.getContent().size());
        assertEquals(dto, result.getContent().get(0));
    }

    @Test
    void getPeopleByNameOrAge_shouldReturnList() {
        List<Person> people = Arrays.asList(new Person(), new Person());
        List<PersonNoPasswordAndUsername> mapped = Arrays.asList(
    new PersonNoPasswordAndUsername(1L, "A", "B", "a@b.com", "123", "2000-01-01", 25),
    new PersonNoPasswordAndUsername(2L, "C", "D", "c@d.com", "456", "1990-01-01", 30)
);
        when(personRepository.findByNameOrAge(anyString(), any(), any())).thenReturn(people);
        when(personMapper.transformPersonToPersonWithNoPasswordAndUsername(people)).thenReturn(mapped);
        List<PersonNoPasswordAndUsername> result = personServiceImpl.getPeopleByNameOrAge("John", 30, PageRequest.of(0, 10));
        assertEquals(2, result.size());
    }

    @Test
    void deletePersonById_shouldDeleteAndReturnTrue() {
        when(personRepository.existsById(1L)).thenReturn(true);
        boolean result = personServiceImpl.deletePersonById(1L);
        assertTrue(result);
        verify(personRepository).deleteById(1L);
    }

    @Test
    void deletePersonById_shouldReturnFalseIfNotExists() {
        when(personRepository.existsById(1L)).thenReturn(false);
        boolean result = personServiceImpl.deletePersonById(1L);
        assertFalse(result);
        verify(personRepository, never()).deleteById(anyLong());
    }

    @Test
    void updatePersonById_shouldUpdateAndReturnDTO() {
        Person person = new Person();
        PersonDTO dto = new PersonDTO();
        Person updated = new Person();
        PersonDTO updatedDto = new PersonDTO();
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        doNothing().when(personMapper).updatePersonFromDto(dto, person);
        when(personRepository.save(person)).thenReturn(updated);
        when(personMapper.transformPersonToPersonDTO(updated)).thenReturn(updatedDto);
        PersonDTO result = personServiceImpl.updatePersonById(1L, dto);
        assertEquals(updatedDto, result);
    }

    @Test
    void updatePersonById_shouldThrowIfNotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PersonDoesNotExistException.class, () -> personServiceImpl.updatePersonById(1L, new PersonDTO()));
    }

    @Test
    void getPersonById_shouldReturnDTO() {
        Person person = new Person();
        PersonDTO dto = new PersonDTO();
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(personMapper.transformPersonToPersonDTO(person)).thenReturn(dto);
        PersonDTO result = personServiceImpl.getPersonById(1L);
        assertEquals(dto, result);
    }

    @Test
    void getPersonById_shouldThrowIfNotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PersonDoesNotExistException.class, () -> personServiceImpl.getPersonById(1L));
    }
}
