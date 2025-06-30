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
    void transformPersonDTOtoPerson_andBack() {
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
        Person person = mapper.transformPersonDTOtoPerson(dto);
        assertEquals(dto.getName(), person.getName());
        assertEquals(dto.getSurname(), person.getSurname());
        assertEquals(dto.getEmail(), person.getEmail());
        assertEquals(dto.getPhone(), person.getPhone());
        assertEquals(dto.getDateOfBirth(), person.getDateOfBirth());
        assertEquals(dto.getAge(), person.getAge());
        assertEquals(dto.getUsername(), person.getUsername());
        assertEquals(dto.getPassword(), person.getPassword());

        PersonDTO mappedBack = mapper.transformPersonToPersonDTO(person);
        assertEquals(person.getName(), mappedBack.getName());
        assertEquals(person.getSurname(), mappedBack.getSurname());
    }

    @Test
    void transformPersonToPersonWithNoPasswordAndUsername() {
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
        List<PersonNoPasswordAndUsername> result = mapper.transformPersonToPersonWithNoPasswordAndUsername(Arrays.asList(person));
        assertEquals(1, result.size());
        assertEquals(person.getName(), result.get(0).getName());
        assertEquals(person.getSurname(), result.get(0).getSurname());
        assertEquals(person.getEmail(), result.get(0).getEmail());
        assertEquals(person.getPhone(), result.get(0).getPhone());
        assertEquals(person.getDateOfBirth(), result.get(0).getDateOfBirth());
        assertEquals(person.getAge(), result.get(0).getAge());
    }
}
