package ub.fet.smartschool.dao;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class FacultyDAO {
    @NotBlank
    @Size(max = 30)
    private String facultyName;
    @NotBlank
    @Size(max = 5)
    private String facultyCode;
}
