package ub.fet.smartschool.dao;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TeacherCourseRetreive {

    @Size(max = 50)
    private String courseName;

    @NotBlank
    @Size(max = 10)
    private String courseCode;

    @NotBlank
    @Size(max = 5)
    private long courseLevel;
}
