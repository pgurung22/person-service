package com.glue.person_service.mapper;

import com.glue.person_service.domain.PersonDTO;
import com.glue.person_service.domain.PersonNoPasswordAndUsername;
import com.glue.person_service.domain.entity.Person;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonMapperTest {
    private final PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

    @Test
    void transformPersonDTOtoPerson_shouldMapFields() {
        PersonDTO dto = PersonDTO.builder().id(1L).name("John").surname("Doe").email("john@doe.com").phone("123").dateOfBirth("2000-01-01").age(25).username("johndoe").password("pass123").build();
        Person person = mapper.transformPersonDTOtoPerson(dto);
        assertEquals(dto.getName(), person.getName());
        assertEquals(dto.getSurname(), person.getSurname());
        assertEquals(dto.getEmail(), person.getEmail());
        assertEquals(dto.getPhone(), person.getPhone());
        assertEquals(dto.getDateOfBirth(), person.getDateOfBirth());
        assertEquals(dto.getAge(), person.getAge());
        assertEquals(dto.getUsername(), person.getUsername());
        assertEquals(dto.getPassword(), person.getPassword());
    }

    @Test
    void transformPersonToPersonDTO_shouldMapFields() {
        Person person = Person.builder().id(1L).name("John").surname("Doe").email("john@doe.com").phone("123").dateOfBirth("2000-01-01").age(25).username("johndoe").password("pass123").build();
        PersonDTO dto = mapper.transformPersonToPersonDTO(person);
        assertEquals(person.getName(), dto.getName());
        assertEquals(person.getSurname(), dto.getSurname());
        assertEquals(person.getEmail(), dto.getEmail());
        assertEquals(person.getPhone(), dto.getPhone());
        assertEquals(person.getDateOfBirth(), dto.getDateOfBirth());
        assertEquals(person.getAge(), dto.getAge());
        assertEquals(person.getUsername(), dto.getUsername());
        assertEquals(person.getPassword(), dto.getPassword());
    }

    @Test
    void transformPersonToPersonWithNoPasswordAndUsername_shouldMapList() {
        Person p1 = Person.builder().id(1L).name("A").surname("B").build();
        Person p2 = Person.builder().id(2L).name("C").surname("D").build();
        List<Person> people = Arrays.asList(p1, p2);
        List<PersonNoPasswordAndUsername> result = mapper.transformPersonToPersonWithNoPasswordAndUsername(people);
        assertEquals(2, result.size());
    }

    @Test
    void updatePersonFromDto_shouldUpdateFields() {
        PersonDTO dto = PersonDTO.builder().name("NewName").build();
        Person person = Person.builder().name("OldName").build();
        mapper.updatePersonFromDto(dto, person);
        assertEquals("NewName", person.getName());
    }
}
