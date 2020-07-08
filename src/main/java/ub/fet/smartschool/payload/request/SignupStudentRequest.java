package ub.fet.smartschool.payload.request;


import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SignupStudentRequest {

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
    @Size(max = 50)
    private String faculty;

    @NotBlank
    @Size(max = 50)
    private String department;

}
