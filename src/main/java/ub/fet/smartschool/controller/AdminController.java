package ub.fet.smartschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ub.fet.smartschool.dao.UpdateStaffDAO;
import ub.fet.smartschool.model.Admin;
import ub.fet.smartschool.model.Staff;
import ub.fet.smartschool.repository.AdminRepository;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminRepository adminRepository;

    @GetMapping("/admin-name/{name}")
    public ResponseEntity<?> getParticularAdminByUsername(@PathVariable("name") String name){
        List<Admin> admin=adminRepository.findAll().stream().filter(
                e->e.getUsername().toLowerCase().contains(name.toLowerCase())
        ).collect(Collectors.toList());
        return ResponseEntity.ok(admin);
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllAdmin(){
        return ResponseEntity.ok(adminRepository.findAll());
    }

    @DeleteMapping("/delete/{name}")
        //@PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteAdmin(@PathVariable("name") String  name) {
        long stdmatr=adminRepository.findByRealnames(name).get().getId();
        adminRepository.deleteById(stdmatr);
        return ResponseEntity.ok("admin deleted");
    }


    @PutMapping("/update/{id}")
        // @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    Admin updateAdmin(@RequestBody UpdateStaffDAO updateStaffDAO, @PathVariable("id") long id) {

        return adminRepository.findById(id)
                .map(l-> {
                    l.setAddress(updateStaffDAO.getAddress());
                    l.setDob(updateStaffDAO.getDob());
                    l.setEmail(updateStaffDAO.getEmail());
                    l.setNationalid(updateStaffDAO.getNationalid());
                    l.setSex(updateStaffDAO.getSex());
                    l.setRealnames(updateStaffDAO.getRealname());
                    return adminRepository.save(l);
                })
                .orElseGet(() -> {
                    return null;
                });
    }

}
