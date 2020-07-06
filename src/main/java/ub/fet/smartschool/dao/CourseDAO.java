package ub.fet.smartschool.dao;

import lombok.Data;
import ub.fet.smartschool.model.ESemester;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CourseDAO {
    @NotBlank
    @Size(max = 50)
    private String courseName;

    @NotBlank
    @Size(max = 10)
    private String courseCode;

    @NotBlank
    @Size(max = 5)
    private long courseLevel;

    @NotBlank
    @Size(max = 5)
    private long courseMarks;

    private long semester;

    @NotBlank
    @Size(max = 5)
    private String departmentCode;

}
