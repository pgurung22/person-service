package com.glue.person_service.mapper;

import com.glue.person_service.domain.PersonDTO;
import com.glue.person_service.domain.PersonNoPasswordAndUsername;
import com.glue.person_service.domain.entity.Person;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    Person transformPersonDTOtoPerson(PersonDTO personDTO);
    PersonDTO transformPersonToPersonDTO(Person person);
    List<PersonNoPasswordAndUsername> transformPersonToPersonWithNoPasswordAndUsername(List<Person> people);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) //to not throw an error when sending the PUT request and some fields are null
    void updatePersonFromDto(PersonDTO personDTO, @MappingTarget Person person);


}
