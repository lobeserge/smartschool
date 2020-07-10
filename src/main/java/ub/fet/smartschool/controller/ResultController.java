package ub.fet.smartschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ub.fet.smartschool.dao.FacultyDAO;
import ub.fet.smartschool.dao.ResultDAO;
import ub.fet.smartschool.dao.StudentResultDAO;
import ub.fet.smartschool.model.Result;
import ub.fet.smartschool.model.Student;
import ub.fet.smartschool.repository.CourseRepository;
import ub.fet.smartschool.repository.ResultRepository;
import ub.fet.smartschool.repository.StudentRepository;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/result")
public class ResultController {

    @Autowired
    ResultRepository resultRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<?> addResult(@Valid @RequestBody ResultDAO resultDAO) {
        Result result = new Result();
        if(studentRepository.existsByMatricule(resultDAO.getMatricule()) &&
                courseRepository.existsByCourseCode(resultDAO.getCourseCode())){
            result.setStudent_marks(resultDAO.getStudent_marks());

            result.setLocalDateTime(LocalDateTime.now());

            result.setStudent(studentRepository.findByMatricule(resultDAO.getMatricule()).get());
            result.setCourse(courseRepository.findByCourseCode(resultDAO.getCourseCode()).get());
           if(resultDAO.getStudent_marks()>=80){
               result.setStatus("pass");
               result.setGrade("A");
           }
           else if(resultDAO.getStudent_marks()>=70 && resultDAO.getStudent_marks()<=79){
               result.setStatus("pass");
               result.setGrade("B+");
           }
           else if(resultDAO.getStudent_marks()>=60 && result.getStudent_marks()<=69){
               result.setStatus("pass");
               result.setGrade("B");
           }
           else if(resultDAO.getStudent_marks()>=55 && result.getStudent_marks()<=59){
               result.setStatus("pass");
               result.setGrade("C+");
           }
           else if(resultDAO.getStudent_marks()>=50 && result.getStudent_marks()<=54){
               result.setStatus("pass");
               result.setGrade("C");
           }
           else if(resultDAO.getStudent_marks()>=45 && result.getStudent_marks()<=49){
               result.setStatus("fail");
               result.setGrade("D+");
           }
           else if(resultDAO.getStudent_marks()>=40 && result.getStudent_marks()<=44){
               result.setStatus("fail");
               result.setGrade("D");
           }
           else{
               result.setStatus("fail");
               result.setGrade("F");
           }
        }
        resultRepository.save(result);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/student/{matricule}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<?> getStudentProfile(@PathVariable("matricule") String mat){

        List<Result> results=resultRepository.findAll().stream()
                .filter(e->e.getStudent().getMatricule().equals(mat)).collect(Collectors.toList());
        List<StudentResultDAO> studentResultDAOS=new ArrayList<>();
        for(Result result:results){
            StudentResultDAO studentResultDAO=new StudentResultDAO();
            studentResultDAO.setCourseCode(result.getCourse().getCourseCode());
            studentResultDAO.setCourseName(result.getCourse().getCourseName());
            studentResultDAO.setGrade(result.getGrade());
            studentResultDAO.setStudent_marks(result.getStudent_marks());
            studentResultDAOS.add(studentResultDAO);
        }

        return ResponseEntity.ok(studentResultDAOS);
    }






}
