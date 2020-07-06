package ub.fet.smartschool.payload.request;


import lombok.Data;
import ub.fet.smartschool.model.Role;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class SignupStudentRequest {
    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 20)
    private String realname;

    @NotBlank
    @Size(max = 20)
    private String nationalid;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max = 20)
    private String address;

    @Size(max = 1)
    private char sex;

    private LocalDateTime regdate;

    private LocalDate dob;

    @NotBlank
    @Size(max = 50)
    private String faculty;

    @NotBlank
    @Size(max = 50)
    private String department;

}
