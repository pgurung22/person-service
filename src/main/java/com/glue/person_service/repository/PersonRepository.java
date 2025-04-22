package com.glue.person_service.repository;
import com.glue.person_service.domain.entity.Person;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    long count();
    List<Person> findByNameOrAge(String name, Integer age, PageRequest pageRequest);





}
