package ub.fet.smartschool.dao;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileFee {
    private String student_name;
    private String student_email;
    private String student_faculty;
    private String student_dept;
    private String student_matricule;
    private String student_level;
    private String address;
    private LocalDate dob;
    private long amount;
    private long balance;

}
