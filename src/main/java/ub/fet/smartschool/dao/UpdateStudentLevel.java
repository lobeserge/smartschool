package ub.fet.smartschool.dao;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UpdateStudentLevel {
    @Size(max = 5)
    private long level;
}
