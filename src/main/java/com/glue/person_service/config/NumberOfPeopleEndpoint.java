package com.glue.person_service.config;

import com.glue.person_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "numberOfPeopleEndpoint")
@RequiredArgsConstructor
public class NumberOfPeopleEndpoint {

    private final PersonRepository personRepository;

    @ReadOperation
    public NumberOfPeople getNumberOfPeople() {
        long count = personRepository.count(); //get count from repo
        return new NumberOfPeople(count);
    }

    public record NumberOfPeople(long peopleCount) {
    }

}
