package ub.fet.smartschool.dao;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class FeeDAO {

    @Size(max = 9)
    private long amount;

    @Size(max = 20)
    private String matricule;



}
