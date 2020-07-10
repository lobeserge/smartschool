package ub.fet.smartschool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 9)
    private long student_marks;

    private String status;

    private String grade;

    private LocalDateTime localDateTime;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;



}
