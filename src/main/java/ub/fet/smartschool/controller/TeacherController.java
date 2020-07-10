package ub.fet.smartschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ub.fet.smartschool.dao.AssignTeacherCourseDAO;
import ub.fet.smartschool.dao.RegStudentCourseDAO;
import ub.fet.smartschool.dao.TeacherCourseRetreive;
import ub.fet.smartschool.model.AssignTeacherCourse;
import ub.fet.smartschool.model.RegStudentCourse;
import ub.fet.smartschool.repository.AssignTeacherCourseRepository;
import ub.fet.smartschool.repository.CourseRepository;
import ub.fet.smartschool.repository.StaffRepository;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired
    AssignTeacherCourseRepository assignTeacherCourseRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StaffRepository staffRepository;

    @PostMapping("/add/course")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addTeacherCourse(@Valid @RequestBody AssignTeacherCourseDAO assignTeacherCourseDAO) {
        AssignTeacherCourse assignTeacherCourse  = new AssignTeacherCourse();
        if (staffRepository.existsByRealnames(assignTeacherCourseDAO.getRealnames()) && courseRepository
                .existsByCourseCode(assignTeacherCourseDAO.getCourseCode())) {
            assignTeacherCourse.setCourse(courseRepository.findByCourseCode(assignTeacherCourseDAO.getCourseCode()).get());
            assignTeacherCourse.setStaff(staffRepository.findByRealnames(assignTeacherCourseDAO.getRealnames()).get());
            assignTeacherCourse.setRegisteredAt(LocalDateTime.now());
            assignTeacherCourseRepository.save(assignTeacherCourse);
        }
        return ResponseEntity.ok(assignTeacherCourse);
    }

    @GetMapping("/courses/{teacherid}")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<?> getTecahersCourse(@PathVariable("teacherid") String teacherid){

        List<AssignTeacherCourse> assignTeacherCourseList=assignTeacherCourseRepository.findAll().
                stream().filter(
                        e->e.getStaff().getRealnames().equals(staffRepository.findByRealnames(teacherid).get().getRealnames())
        ).collect(Collectors.toList());

        List<TeacherCourseRetreive> tf=new ArrayList<>();
        for(AssignTeacherCourse t:assignTeacherCourseList){
            TeacherCourseRetreive tm=new TeacherCourseRetreive();
            tm.setCourseCode(t.getCourse().getCourseCode());
            tm.setCourseName(t.getCourse().getCourseName());
            tm.setCourseLevel(t.getCourse().getCourseLevel());
            tf.add(tm);
        }
        return ResponseEntity.ok(tf);
    }







}
