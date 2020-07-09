package ub.fet.smartschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ub.fet.smartschool.dao.RegStudentCourseDAO;
import ub.fet.smartschool.dao.StudentProfile;
import ub.fet.smartschool.model.RegStudentCourse;
import ub.fet.smartschool.model.Student;
import ub.fet.smartschool.repository.CourseRepository;
import ub.fet.smartschool.repository.RegStudentCourseRepository;
import ub.fet.smartschool.repository.StudentRepository;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    RegStudentCourseRepository regStudentCourseRepository;

    @PostMapping("/add/course")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<?> regCourse(@Valid @RequestBody RegStudentCourseDAO regStudentCourseDAO) {
        RegStudentCourse regStudentCourse = new RegStudentCourse();
        if (studentRepository.existsByMatricule(regStudentCourseDAO.getMatricule()) && courseRepository
                .existsByCourseCode(regStudentCourseDAO.getCourseCode())) {
            regStudentCourse = new RegStudentCourse();
            regStudentCourse.setCourse(courseRepository.findByCourseCode(regStudentCourseDAO.getCourseCode()).get());
            regStudentCourse.setStudent(studentRepository.findByMatricule(regStudentCourseDAO.getMatricule()).get());
            regStudentCourse.setRegisteredAt(LocalDateTime.now());
            regStudentCourseRepository.save(regStudentCourse);
        }
        return ResponseEntity.ok(regStudentCourseRepository.save(regStudentCourse));
    }

    @GetMapping("/profile/{matricule}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<?> getStudentProfile(@PathVariable("matricule") String mat) {

        Student student = studentRepository.findByMatricule(mat).get();
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setStudent_dept(student.getDepartment().getDepartmentName());
        studentProfile.setStudent_email(student.getEmail());
        studentProfile.setStudent_name(student.getRealname());
        studentProfile.setStudent_faculty(student.getFaculty().getFacultyName());
        studentProfile.setStudent_level(String.valueOf(student.getLevel()));
        studentProfile.setStudent_matricule(student.getMatricule());

        return ResponseEntity.ok(studentProfile);

    }
}
