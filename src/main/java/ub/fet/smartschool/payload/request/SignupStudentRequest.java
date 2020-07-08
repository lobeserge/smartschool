package ub.fet.smartschool.payload.request;


import lombok.Data;
import ub.fet.smartschool.model.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class SignupStudentRequest {


    private Long id;

    @Size(max = 20)
    private String username;


    @Size(max = 20)
    private String realname;


    @Size(max = 20)
    private String nationalid;


    @Size(max = 50)
    @Email
    private String email;


    @Size(max = 120)
    private String password;


    @Size(max = 20)
    private String address;

    @Size(max = 1)
    private char sex;

    private LocalDateTime regdate;

    private LocalDate dob;

    @NotBlank
    @Size(max = 20)
    private String matricule;

    @NotBlank
    @Size(max = 50)
    private String faculty;

    @NotBlank
    @Size(max = 50)
    private String department;

}
