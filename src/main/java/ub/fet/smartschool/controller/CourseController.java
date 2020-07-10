package ub.fet.smartschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ub.fet.smartschool.dao.CourseDAO;
import ub.fet.smartschool.dao.DepartmentDAO;
import ub.fet.smartschool.dao.StudentCourseRetreive;
import ub.fet.smartschool.model.RegStudentCourse;
import ub.fet.smartschool.repository.CourseRepository;
import ub.fet.smartschool.repository.RegStudentCourseRepository;
import ub.fet.smartschool.service.CourseService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    CourseService courseService;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    RegStudentCourseRepository reg;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addCourset(@Valid @RequestBody CourseDAO courseDAO) {
        return ResponseEntity.ok(courseService.addCourse(courseDAO));
    }


    @GetMapping("/student/{coursecode}")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<?> getStudentsForACourse(@PathVariable("coursecode") String coursecode){
        List<RegStudentCourse> regcourse=reg.findAll().stream().filter(
                e->e.getCourse().getCourseCode().equals(coursecode)
        ).collect(Collectors.toList());

        List<StudentCourseRetreive> studentCourseRetreives= new ArrayList<>();
        for(RegStudentCourse sr:regcourse){
            StudentCourseRetreive studentCourseRetreive=new StudentCourseRetreive();
            studentCourseRetreive.setEmail(sr.getStudent().getEmail());
            studentCourseRetreive.setMatricule(sr.getStudent().getMatricule());
            studentCourseRetreive.setRealname(sr.getStudent().getRealname());
            studentCourseRetreive.setSex(sr.getStudent().getSex());
            studentCourseRetreives.add(studentCourseRetreive);

        }
        return ResponseEntity.ok(studentCourseRetreives);

    }



}
