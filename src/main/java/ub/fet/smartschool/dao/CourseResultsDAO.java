package ub.fet.smartschool.dao;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CourseResultsDAO {
    @Size(max = 50)
    private String StudentName;

    @Size(max = 20)
    private String matricule;

    @Size(max = 9)
    private long student_marks;

    private String grade;
}
