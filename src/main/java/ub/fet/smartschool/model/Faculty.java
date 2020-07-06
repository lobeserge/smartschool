package ub.fet.smartschool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 50)
    private String facultyName;

    @NotBlank
    @Size(max = 5)
    private String facultyCode;

    private LocalDateTime creationDate;

    @JsonIgnore
    @OneToMany(mappedBy = "faculty")
    private Set<Department> department;

    @JsonIgnore
    @OneToMany(mappedBy = "faculty")
    private Set<Student> students;

}
