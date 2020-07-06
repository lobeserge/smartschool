package ub.fet.smartschool.dao;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class DepartmentDAO {
    @NotBlank
    @Size(max = 20)
    private String departmentName;

    @NotBlank
    @Size(max = 5)
    private String departmentCode;

    @NotBlank
    @Size(max = 5)
    private String facultyCode;

}
