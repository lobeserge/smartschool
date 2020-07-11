package ub.fet.smartschool.dao;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ResultUpdateDAO {
    @Size(max = 9)
    private long student_marks;
}
