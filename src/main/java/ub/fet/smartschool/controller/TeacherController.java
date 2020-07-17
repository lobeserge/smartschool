package ub.fet.smartschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ub.fet.smartschool.dao.*;
import ub.fet.smartschool.model.AssignTeacherCourse;
import ub.fet.smartschool.model.Staff;
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
   // @PreAuthorize("hasRole('ADMIN')")
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


    @GetMapping("/teacher-name/{name}")
    public ResponseEntity<?> getParticularTeacherByName(@PathVariable("name") String name){
        List<Staff> teacher=staffRepository.findAll().stream().filter(
                e->e.getRealnames().toLowerCase().contains(name.toLowerCase())
        ).collect(Collectors.toList());
        return ResponseEntity.ok(teacher);
    }


    @DeleteMapping("/delete/{name}")
    //@PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteTeacher(@PathVariable("name") String  name) {
        long stdmatr=staffRepository.findByRealnames(name).get().getId();
        staffRepository.deleteById(stdmatr);
        return ResponseEntity.ok("staff deleted");
    }

    @PutMapping("/update/{id}")
   // @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    Staff updateStaff(@RequestBody UpdateStaffDAO updateStaffDAO, @PathVariable("id") long id) {

        return staffRepository.findById(id)
                .map(l-> {
                    l.setAddress(updateStaffDAO.getAddress());
                    l.setDob(updateStaffDAO.getDob());
                    l.setEmail(updateStaffDAO.getEmail());
                    l.setNationalid(updateStaffDAO.getNationalid());
                    l.setSex(updateStaffDAO.getSex());
                   l.setRealnames(updateStaffDAO.getRealname());
                    return staffRepository.save(l);
                })
                .orElseGet(() -> {
                    return null;
                });
    }


}
