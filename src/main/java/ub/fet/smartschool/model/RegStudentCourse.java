package ub.fet.smartschool.model;

import lombok.Data;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
public class RegStudentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    Course course;

    private LocalDateTime registeredAt;

}
