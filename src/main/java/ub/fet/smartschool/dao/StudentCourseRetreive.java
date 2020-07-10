package ub.fet.smartschool.dao;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class StudentCourseRetreive {

    @Size(max = 20)
    private String realname;

    @Size(max = 20)
    private String matricule;

    @Size(max = 50)
    @Email
    private String email;

    @Size(max = 1)
    private char sex;

}
