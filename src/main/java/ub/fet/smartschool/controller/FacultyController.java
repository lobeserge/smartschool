package ub.fet.smartschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ub.fet.smartschool.dao.FacultyDAO;
import ub.fet.smartschool.dao.UpdateDepartmentDAO;
import ub.fet.smartschool.dao.UpdateFacultyDAO;
import ub.fet.smartschool.model.Department;
import ub.fet.smartschool.model.Faculty;
import ub.fet.smartschool.payload.request.SignupAdminRequest;
import ub.fet.smartschool.repository.FacultyRepository;
import ub.fet.smartschool.service.FacultyService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/faculty")
public class FacultyController {
	@Autowired
	private FacultyService facultyService;

	@Autowired
	private FacultyRepository facultyRepository;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addFaculty(@Valid @RequestBody FacultyDAO facultyDAO) {
		return ResponseEntity.ok(facultyService.addFaculty(facultyDAO));
	}

	@GetMapping("/all")
	public ResponseEntity<?> getFaculties(){
		return ResponseEntity.ok(facultyRepository.findAll());
	}

	@GetMapping("/code/{facultyid}")
	public ResponseEntity<?> getParticularFacultyByCode(@PathVariable("facultyid") String facultyid){
		Faculty fact=facultyRepository.findByFacultyCode(facultyid).get();
		return ResponseEntity.ok(fact);
	}


	@GetMapping("/name/{factname}")
	public ResponseEntity<?> getParticularDepartmentByName(@PathVariable("factname") String factname){
		List<Faculty> faculties=facultyRepository.findAll().stream().filter(
				e->e.getFacultyName().toLowerCase().contains(factname.toLowerCase())
		).collect(Collectors.toList());
		return ResponseEntity.ok(faculties);
	}

	@DeleteMapping("/delete/{facultyid}")
	ResponseEntity<?> deleteFaculty(@PathVariable("facultyid") String  facultyid) {
		int factid=facultyRepository.findByFacultyCode(facultyid).get().getId();
		facultyRepository.deleteById(factid);
		return ResponseEntity.ok("faculty deleted");
	}

	@PutMapping("/update/{facultyid}")
	Faculty updateFaculty(@RequestBody UpdateFacultyDAO updateFacultyDAO, @PathVariable("facultyid") String facultyid) {

		return facultyRepository.findByFacultyCode(facultyid)
				.map(l-> {
					l.setFacultyName(updateFacultyDAO.getFacultyName());
					return facultyRepository.save(l);
				})
				.orElseGet(() -> {
					return null;
				});
	}

}
