package ub.fet.smartschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ub.fet.smartschool.dao.DepartmentDAO;
import ub.fet.smartschool.dao.UpdateDepartmentDAO;
import ub.fet.smartschool.model.Department;
import ub.fet.smartschool.repository.DepartmentRepository;
import ub.fet.smartschool.service.DepartmentService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/department")
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;

	@Autowired
	DepartmentRepository departmentRepository;

	@PostMapping("/add")
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addDept(@Valid @RequestBody DepartmentDAO departmentDAO) {
		return ResponseEntity.ok(departmentService.addDepartment(departmentDAO));
	}


	@GetMapping("/all")
	public ResponseEntity<?> getDepartments(){
		return ResponseEntity.ok(departmentRepository.findAll());
	}

	@GetMapping("/code/{deptid}")
	public ResponseEntity<?> getParticularDepartmentByCode(@PathVariable("deptid") String deptid){
		Department dept=departmentRepository.findByDepartmentCode(deptid).get();
		return ResponseEntity.ok(dept);
	}

	@GetMapping("/name/{deptname}")
	public ResponseEntity<?> getParticularDepartmentByName(@PathVariable("deptname") String deptname){
		List<Department> departmentList=departmentRepository.findAll().stream().filter(
				e->e.getDepartmentName().toLowerCase().contains(deptname.toLowerCase())
		).collect(Collectors.toList());
		return ResponseEntity.ok(departmentList);
	}

	@DeleteMapping("/delete/{departid}")
//	@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<?> deleteDepartment(@PathVariable("departid") String  departid) {
		int deptid=departmentRepository.findByDepartmentCode(departid).get().getId();
		departmentRepository.deleteById(deptid);
		return ResponseEntity.ok("department deleted");
	}

	@PutMapping("/update/{departid}")
//	@PreAuthorize("hasRole('ADMIN')")
	Department updateDepartment(@RequestBody UpdateDepartmentDAO departmentName, @PathVariable("departid") String departid) {

		return departmentRepository.findByDepartmentCode(departid)
				.map(l-> {
					l.setDepartmentName(departmentName.getDepartmentName());
					return departmentRepository.save(l);
				})
				.orElseGet(() -> {
					return null;

				});
	}


}
