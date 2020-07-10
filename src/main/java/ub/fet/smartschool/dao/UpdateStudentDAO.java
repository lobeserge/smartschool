package ub.fet.smartschool.dao;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UpdateStudentDAO {

    @Size(max = 20)
    private String realname;

    @Size(max = 20)
    private String nationalid;

    @Email
    private String email;

    private LocalDate dob;

    @Size(max = 1)
    private char sex;

    @Size(max = 20)
    private String address;
}
