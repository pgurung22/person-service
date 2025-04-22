package com.glue.person_service.service;
import com.glue.person_service.domain.PersonDTO;
import com.glue.person_service.domain.PersonNoPasswordAndUsername;
import com.glue.person_service.domain.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface PersonService {
   Person savePerson(PersonDTO person);
   Page<PersonDTO> getPeople(PageRequest pageRequest);
   List<PersonNoPasswordAndUsername> getPeopleByNameOrAge(String name, Integer age, PageRequest pageRequest);
   boolean deletePersonById(Long id);
   PersonDTO updatePersonById(Long id, PersonDTO person);
   PersonDTO getPersonById(Long id);

}
