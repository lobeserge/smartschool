package ub.fet.smartschool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 50)
    private String departmentName;

    @NotBlank
    @Size(max = 5)
    private String departmentCode;

    private LocalDateTime creationDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private Set<Student> students;

    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private Set<Course> courses;

}
