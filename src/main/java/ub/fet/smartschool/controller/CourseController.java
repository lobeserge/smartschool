package ub.fet.smartschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ub.fet.smartschool.dao.CourseDAO;
import ub.fet.smartschool.dao.DepartmentDAO;
import ub.fet.smartschool.service.CourseService;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    CourseService courseService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addCourset(@Valid @RequestBody CourseDAO courseDAO) {
        return ResponseEntity.ok(courseService.addCourse(courseDAO));
    }

}
