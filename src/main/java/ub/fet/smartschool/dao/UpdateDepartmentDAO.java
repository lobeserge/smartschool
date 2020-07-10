package ub.fet.smartschool.dao;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UpdateDepartmentDAO {

    @Size(max = 50)
    private String departmentName;
}
