package ub.fet.smartschool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String realname;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 20)
    private String matricule;

    @NotBlank
    @Size(max = 20)
    private String nationalid;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max = 20)
    private String address;

    @Size(max = 1)
    private char sex;

    private LocalDateTime regdate;

    @NotBlank
    @Size(max = 5)
    private long level;

    private LocalDate dob;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "student_roles",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;


    @ManyToMany
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    Set<Course> registeredCourse;


    @JsonIgnore
    @OneToMany(mappedBy = "student")
    Set<RegStudentCourse> registrations;

}
