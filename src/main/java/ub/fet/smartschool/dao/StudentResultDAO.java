package ub.fet.smartschool.dao;

import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class StudentResultDAO{
    @Size(max = 10)
    private String courseCode;

    @Size(max = 50)
    private String courseName;

    @Size(max = 9)
    private long student_marks;

    private String grade;


}
