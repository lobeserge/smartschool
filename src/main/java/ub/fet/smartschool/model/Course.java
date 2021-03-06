package ub.fet.smartschool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 50)
    private String courseName;

    @NotBlank
    @Size(max = 10)
    private String courseCode;

    @NotBlank
    @Size(max = 5)
    private long courseLevel;

    @NotBlank
    @Size(max = 5)
    private long courseMarks;

    private ESemester semester;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;


    @JsonIgnore
    @OneToMany(mappedBy = "course")
    Set<RegStudentCourse> registrations;

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    Set<AssignTeacherCourse> assignTeacherCourses;


    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private Set<Result> results;

}
