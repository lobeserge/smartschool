package ub.fet.smartschool.dao;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UpdateFacultyDAO {

    @Size(max = 50)
    private String facultyName;
}
