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
        PageRequest pageRequest = PageRequest.of(0, 10);
        Person person = new Person();
        PersonDTO dto = new PersonDTO();
        Page<Person> personPage = new PageImpl<>(Collections.singletonList(person));
        when(personRepository.findAll(pageRequest)).thenReturn(personPage);
        when(personMapper.transformPersonToPersonDTO(person)).thenReturn(dto);
        Page<PersonDTO> result = personServiceImpl.getPeople(pageRequest);
        assertEquals(1, result.getContent().size());
        assertEquals(dto, result.getContent().get(0));
    }

    @Test
    void getPeopleByNameOrAge_shouldReturnList() {
        String name = "John";
        Integer age = 30;
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Person> people = Arrays.asList(new Person(), new Person());
        List<PersonNoPasswordAndUsername> mapped = Arrays.asList(
                new PersonNoPasswordAndUsername(1L, "John", "Doe", "john@example.com", "1234567890", "2000-01-01", 30),
                new PersonNoPasswordAndUsername(2L, "Jane", "Smith", "jane@example.com", "0987654321", "1990-02-02", 25)
        );
        when(personRepository.findByNameOrAge(name, age, pageRequest)).thenReturn(people);
        when(personMapper.transformPersonToPersonWithNoPasswordAndUsername(people)).thenReturn(mapped);
        List<PersonNoPasswordAndUsername> result = personServiceImpl.getPeopleByNameOrAge(name, age, pageRequest);
        assertEquals(2, result.size());
    }

    @Test
    void deletePersonById_shouldReturnTrueIfExists() {
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
        Long id = 1L;
        PersonDTO dto = new PersonDTO();
        Person person = new Person();
        Person updated = new Person();
        PersonDTO updatedDto = new PersonDTO();
        when(personRepository.findById(id)).thenReturn(Optional.of(person));
        doNothing().when(personMapper).updatePersonFromDto(dto, person);
        when(personRepository.save(person)).thenReturn(updated);
        when(personMapper.transformPersonToPersonDTO(updated)).thenReturn(updatedDto);
        PersonDTO result = personServiceImpl.updatePersonById(id, dto);
        assertEquals(updatedDto, result);
    }

    @Test
    void updatePersonById_shouldThrowIfNotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PersonDoesNotExistException.class, () -> personServiceImpl.updatePersonById(1L, new PersonDTO()));
    }

    @Test
    void getPersonById_shouldReturnDTOIfFound() {
        Long id = 1L;
        Person person = new Person();
        PersonDTO dto = new PersonDTO();
        when(personRepository.findById(id)).thenReturn(Optional.of(person));
        when(personMapper.transformPersonToPersonDTO(person)).thenReturn(dto);
        PersonDTO result = personServiceImpl.getPersonById(id);
        assertEquals(dto, result);
    }

    @Test
    void getPersonById_shouldThrowIfNotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PersonDoesNotExistException.class, () -> personServiceImpl.getPersonById(1L));
    }
}
