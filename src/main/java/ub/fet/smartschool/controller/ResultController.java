package ub.fet.smartschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ub.fet.smartschool.dao.*;
import ub.fet.smartschool.model.Result;
import ub.fet.smartschool.repository.CourseRepository;
import ub.fet.smartschool.repository.ResultRepository;
import ub.fet.smartschool.repository.StudentRepository;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public ResponseEntity<?> getStudentResults(@PathVariable("matricule") String mat){

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

    @GetMapping("/course/{coursecode}")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<?> getCourseResult(@PathVariable("coursecode") String coursecode){

        List<CourseResultsDAO> courseResultsDAOS=new ArrayList<>();
        List<Result> results=resultRepository.findAll().stream().filter(
                e->e.getCourse().getCourseCode().
                        equals(courseRepository.findByCourseCode(coursecode).get().getCourseCode())
        ).collect(Collectors.toList());

        for(Result r:results){
            CourseResultsDAO cr=new CourseResultsDAO();
            cr.setStudentName(r.getStudent().getRealname());
            cr.setMatricule(r.getStudent().getMatricule());
            cr.setStudent_marks(r.getStudent_marks());
            cr.setGrade(r.getGrade());
            courseResultsDAOS.add(cr);
        }
           return ResponseEntity.ok(courseResultsDAOS);
    }

    @DeleteMapping("/delete/student/{studentid}/course/{courseid}")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    ResponseEntity<?> deleteStudentMarks(@PathVariable("studentid") String  studentid,
                                         @PathVariable("courseid") String  courseid) {

        List<Result> result=resultRepository.findAll().stream().
                filter(e->e.getStudent().getMatricule().equals(studentid) &&
                        e.getCourse().getCourseCode().equals(courseid)).collect(Collectors.toList());
        for(Result rs:result){
            resultRepository.deleteById(rs.getId());
        }
        return ResponseEntity.ok("results deleted");
    }


    @PutMapping("/update/student/{matricule}/course/{coursecode}")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    Stream<Result> updateStudentMarks(@RequestBody ResultUpdateDAO resultDAO, @PathVariable("matricule") String matricule,
                               @PathVariable("coursecode") String coursecode) {
        return resultRepository.findAll().stream().filter(
                e->e.getStudent().getMatricule().equals(matricule) &&
                        e.getCourse().getCourseCode().equals(coursecode)).map(
                result->{
                    result.setLocalDateTime(LocalDateTime.now());
                    result.setStudent_marks(resultDAO.getStudent_marks());
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
                                         return resultRepository.save(result);
                                }

        );

    }




}
