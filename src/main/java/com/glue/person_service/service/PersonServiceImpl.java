package com.glue.person_service.service;
import com.glue.person_service.domain.PersonDTO;
import com.glue.person_service.domain.PersonNoPasswordAndUsername;
import com.glue.person_service.domain.entity.Person;
import com.glue.person_service.exception.PersonDoesNotExistException;
import com.glue.person_service.mapper.PersonMapper;
import com.glue.person_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService{

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;
    @Override
    public Person savePerson(PersonDTO personDTO){
        log.info("saving person to DB ...");
        Person person = personMapper.transformPersonDTOtoPerson(personDTO);
        return personRepository.save(person);
    }
    @Override
    public Page<PersonDTO> getPeople(PageRequest pageRequest){
         return personRepository.findAll(pageRequest).map(personMapper::transformPersonToPersonDTO);
    }

    @Override
    public List<PersonNoPasswordAndUsername> getPeopleByNameOrAge(String name, Integer age, PageRequest pageRequest)  {

        List<Person> filteredByNameOrAge = personRepository.findByNameOrAge(name, age, pageRequest);
        return personMapper.transformPersonToPersonWithNoPasswordAndUsername(filteredByNameOrAge);

    }
    @Override
    public boolean deletePersonById (Long id)  {

        if (personRepository.existsById(id)){
            personRepository.deleteById(id);
            return true;
        }
         else {
            return false;
        }
    }

    @Override
    public PersonDTO updatePersonById (Long id, PersonDTO personDTO)  {
        Optional<Person> person = personRepository.findById(id);

        if(person.isPresent()){
            log.info("Updating person with ID " + id);

            Person existingPerson = person.get();

            personMapper.updatePersonFromDto(personDTO, existingPerson);
            Person updatedPerson =  personRepository.save(existingPerson);
            return personMapper.transformPersonToPersonDTO(updatedPerson);
        } else {
            throw new PersonDoesNotExistException("Person you are trying to update does not exist");
        }
    }

    @Override
    public PersonDTO getPersonById(Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            return personMapper.transformPersonToPersonDTO(person.get());
        } else {
            throw new PersonDoesNotExistException("Person with the provided ID does not exist");
        }
    }


}
