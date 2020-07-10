package ub.fet.smartschool.dao;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UpdateCourseDAO {

    @Size(max = 50)
    private String courseName;

}
