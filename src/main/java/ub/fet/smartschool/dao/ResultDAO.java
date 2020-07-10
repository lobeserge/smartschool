package ub.fet.smartschool.dao;

import lombok.Data;


import javax.validation.constraints.Size;

@Data
public class ResultDAO {

    @Size(max = 9)
    private long student_marks;

    @Size(max = 20)
    private String matricule;

    @Size(max = 10)
    private String courseCode;

}
