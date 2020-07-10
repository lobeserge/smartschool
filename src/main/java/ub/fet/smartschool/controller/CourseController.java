package ub.fet.smartschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ub.fet.smartschool.dao.*;
import ub.fet.smartschool.model.Course;
import ub.fet.smartschool.model.Faculty;
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

    @GetMapping("/all")
    public ResponseEntity<?> getCourses(){
        return ResponseEntity.ok(courseRepository.findAll());
    }

    @GetMapping("/name/{coursename}")
    public ResponseEntity<?> getCourseByName(@PathVariable("coursename") String coursename){
        List<Course> courses=courseRepository.findAll().stream().filter(
                e->e.getCourseName().toLowerCase().contains(coursename.toLowerCase())
        ).collect(Collectors.toList());
        return ResponseEntity.ok(courses);
    }
    @GetMapping("/code/{coursecode}")
    public ResponseEntity<?> getParticularCourse(@PathVariable("coursecode") String coursecode){
        Course course=courseRepository.findByCourseCode(coursecode).get();
        return ResponseEntity.ok(course);
    }

    @DeleteMapping("/delete/{coursecode}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteCourse(@PathVariable("coursecode") String  coursecode) {
        long courseid=courseRepository.findByCourseCode(coursecode).get().getId();
        courseRepository.deleteById(courseid);
        return ResponseEntity.ok("course deleted");
    }

    @PutMapping("/update/{courseid}")
    @PreAuthorize("hasRole('ADMIN')")
    Course updateCourse(@RequestBody UpdateCourseDAO updateCourseDAO, @PathVariable("courseid") String courseid) {

        return courseRepository.findByCourseCode(courseid)
                .map(l-> {
                  l.setCourseName(updateCourseDAO.getCourseName());
                    return courseRepository.save(l);
                })
                .orElseGet(() -> {
                    return null;
                });
    }





}
