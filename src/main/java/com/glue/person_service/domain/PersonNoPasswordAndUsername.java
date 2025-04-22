package com.glue.person_service.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class PersonNoPasswordAndUsername {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String dateOfBirth;
    private int age;
}
