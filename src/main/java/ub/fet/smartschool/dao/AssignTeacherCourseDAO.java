package ub.fet.smartschool.dao;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class AssignTeacherCourseDAO {

    @Size(max = 20)
    private String realnames;

    @Size(max = 10)
    private String courseCode;
}
