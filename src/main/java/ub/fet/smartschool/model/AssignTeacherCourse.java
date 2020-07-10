package ub.fet.smartschool.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class AssignTeacherCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    Staff staff;

    @ManyToOne
    @JoinColumn(name = "course_id")
    Course course;

    private LocalDateTime registeredAt;

}
