package ub.fet.smartschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ub.fet.smartschool.dao.FacultyDAO;
import ub.fet.smartschool.payload.request.SignupAdminRequest;
import ub.fet.smartschool.service.FacultyService;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/faculty")
public class FacultyController {
	@Autowired
	private FacultyService facultyService;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addFaculty(@Valid @RequestBody FacultyDAO facultyDAO) {
		return ResponseEntity.ok(facultyService.addFaculty(facultyDAO));
	}

}
