package com.glue.person_service.controller;
import com.glue.person_service.domain.PersonDTO;
import com.glue.person_service.domain.PersonNoPasswordAndUsername;
import com.glue.person_service.service.PersonServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PersonController {
    private final PersonServiceImpl personServiceImpl;

    @PostMapping("/person/save")
    public ResponseEntity<String> savePerson(@Valid @RequestBody PersonDTO person){

        personServiceImpl.savePerson(person);

        return new ResponseEntity<>("Person added successfully", HttpStatus.CREATED);

    }
  @GetMapping("/getPeople")
  public List<PersonDTO> getPeople(@RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) {

      if(null == page) page = 0;
      if(null == pageSize) pageSize = 10;
      return personServiceImpl.getPeople(PageRequest.of(page, pageSize)).getContent(); //if not specified, by default page size is 10

  }
    @GetMapping("/filter")
    public ResponseEntity<?> getPeopleByName(@RequestParam(value = "page", required = false) Integer page,
                                                                             @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                                             @RequestParam String name, @RequestParam(required = false) Integer age) {
        if(null == page) page = 0;
        if(null == pageSize) pageSize = 10;

        List<PersonNoPasswordAndUsername> people = personServiceImpl.getPeopleByNameOrAge(name, age, PageRequest.of(page, pageSize));
        if(!people.isEmpty()){
            return new ResponseEntity<>(people, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No person matches the filter conditions", HttpStatus.NOT_FOUND);
        }


    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePersonById(@PathVariable Long id) {

        boolean deleted = personServiceImpl.deletePersonById(id);

        if(deleted){
            return new ResponseEntity<>("Person removed successfully", HttpStatus.OK);  //just a simple string as response
        } else{
            return new ResponseEntity<>("Person with indicated ID does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePersonById(@PathVariable Long id, @RequestBody PersonDTO updatedPerson) {

        personServiceImpl.updatePersonById(id, updatedPerson);
        return new ResponseEntity<>("Person updated successfully", HttpStatus.CREATED);

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {

        return new ResponseEntity<>(personServiceImpl.getPersonById(id), HttpStatus.CREATED);

    }

}
