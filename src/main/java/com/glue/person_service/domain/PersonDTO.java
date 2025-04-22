package com.glue.person_service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDTO {

    //only some validations are done for demonstration
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("surname")
    private String surname;
    @JsonProperty(value = "email")
    @Email(message = "Invalid email format")
    private String email;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("dateOfBirth")
    private String dateOfBirth;
    @JsonProperty("age")
    @Min(value = 18, message = "Person should be minimum 18 years old")
    private int age;
    @JsonProperty("username")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{5,11}$", message = "username does not match the requirements")
    private String username;
    @JsonProperty("password")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_*!£]{5,11}$", message = "password does not match the requirements")
    private String password;
}
