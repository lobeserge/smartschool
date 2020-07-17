package ub.fet.smartschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ub.fet.smartschool.dao.FeeReceipt;
import ub.fet.smartschool.dao.Profile;
import ub.fet.smartschool.dao.ProfileFee;
import ub.fet.smartschool.model.Fee;
import ub.fet.smartschool.repository.FeeRepository;
import ub.fet.smartschool.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/fee")
public class FeeController {
    @Autowired
    FeeRepository feeRepository;

    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/completed")
    public ResponseEntity<?> getCompleteFees(){
        String status="complete";
        List<Fee> fees=feeRepository.findAll().stream().filter(
                e->e.getStatus().toLowerCase().equals(status)
        ).collect(Collectors.toList());
        List<FeeReceipt> profiles=new ArrayList<>();
        for(Fee fee:fees){
            FeeReceipt studentProfile=new FeeReceipt();
            studentProfile.setStudent_dept(fee.getStudent().getDepartment().getDepartmentName());
            studentProfile.setStudent_email(fee.getStudent().getEmail());
            studentProfile.setStudent_name(fee.getStudent().getRealname());
            studentProfile.setStudent_faculty(fee.getStudent().getFaculty().getFacultyName());
            studentProfile.setStudent_level(String.valueOf(fee.getStudent().getLevel()));
            studentProfile.setStudent_matricule(fee.getStudent().getMatricule());
            studentProfile.setDob(fee.getStudent().getDob());
            studentProfile.setAddress(fee.getStudent().getAddress());
            studentProfile.setAmount(fee.getAmount());
            studentProfile.setBalance(fee.getBalance());
            studentProfile.setStatus(fee.getStatus());
            studentProfile.setDatePaid(fee.getLocalDateTime());
            profiles.add(studentProfile);
        }
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getPendingFees(){
        String status="pending";
        List<Fee> fees=feeRepository.findAll().stream().filter(
                e->e.getStatus().toLowerCase().equals(status)
        ).collect(Collectors.toList());
        List<FeeReceipt> profiles=new ArrayList<>();
        for(Fee fee:fees){
            FeeReceipt studentProfile=new FeeReceipt();
            studentProfile.setStudent_dept(fee.getStudent().getDepartment().getDepartmentName());
            studentProfile.setStudent_email(fee.getStudent().getEmail());
            studentProfile.setStudent_name(fee.getStudent().getRealname());
            studentProfile.setStudent_faculty(fee.getStudent().getFaculty().getFacultyName());
            studentProfile.setStudent_level(String.valueOf(fee.getStudent().getLevel()));
            studentProfile.setStudent_matricule(fee.getStudent().getMatricule());
            studentProfile.setDob(fee.getStudent().getDob());
            studentProfile.setAddress(fee.getStudent().getAddress());
            studentProfile.setAmount(fee.getAmount());
            studentProfile.setBalance(fee.getBalance());
            studentProfile.setStatus(fee.getStatus());
            studentProfile.setDatePaid(fee.getLocalDateTime());
            profiles.add(studentProfile);
        }
        return ResponseEntity.ok(profiles);
    }


    @GetMapping("/student/{matricule}")
    public ResponseEntity<?> getStudentFee(@PathVariable("matricule") String matricule){
        Fee fee=feeRepository.findByStudent(studentRepository.findByMatricule(matricule).get()).get();
            FeeReceipt studentProfile=new FeeReceipt();
        studentProfile.setStudent_dept(fee.getStudent().getDepartment().getDepartmentName());
        studentProfile.setStudent_email(fee.getStudent().getEmail());
        studentProfile.setStudent_name(fee.getStudent().getRealname());
        studentProfile.setStudent_faculty(fee.getStudent().getFaculty().getFacultyName());
        studentProfile.setStudent_level(String.valueOf(fee.getStudent().getLevel()));
        studentProfile.setStudent_matricule(fee.getStudent().getMatricule());
        studentProfile.setDob(fee.getStudent().getDob());
        studentProfile.setAddress(fee.getStudent().getAddress());
        studentProfile.setAmount(fee.getAmount());
        studentProfile.setBalance(fee.getBalance());
        studentProfile.setStatus(fee.getStatus());
        studentProfile.setDatePaid(fee.getLocalDateTime());

        return ResponseEntity.ok(studentProfile);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        List<Fee> fees=feeRepository.findAll();
        List<FeeReceipt> profiles=new ArrayList<>();
        for(Fee fee:fees){
            FeeReceipt studentProfile=new FeeReceipt();
            studentProfile.setStudent_dept(fee.getStudent().getDepartment().getDepartmentName());
            studentProfile.setStudent_email(fee.getStudent().getEmail());
            studentProfile.setStudent_name(fee.getStudent().getRealname());
            studentProfile.setStudent_faculty(fee.getStudent().getFaculty().getFacultyName());
            studentProfile.setStudent_level(String.valueOf(fee.getStudent().getLevel()));
            studentProfile.setStudent_matricule(fee.getStudent().getMatricule());
            studentProfile.setDob(fee.getStudent().getDob());
            studentProfile.setAddress(fee.getStudent().getAddress());
            studentProfile.setAmount(fee.getAmount());
            studentProfile.setBalance(fee.getBalance());
            studentProfile.setStatus(fee.getStatus());
            studentProfile.setDatePaid(fee.getLocalDateTime());
            profiles.add(studentProfile);
        }
        return ResponseEntity.ok(profiles);
    }

}
