package ub.fet.smartschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ub.fet.smartschool.model.Admin;
import ub.fet.smartschool.model.ERole;
import ub.fet.smartschool.model.Role;
import ub.fet.smartschool.model.Student;
import ub.fet.smartschool.payload.request.LoginRequest;
import ub.fet.smartschool.payload.request.SignupAdminRequest;
import ub.fet.smartschool.payload.request.SignupStudentRequest;
import ub.fet.smartschool.payload.response.JwtResponse;
import ub.fet.smartschool.payload.response.MessageResponse;
import ub.fet.smartschool.repository.*;
import ub.fet.smartschool.security.jwt.JwtUtils;
import ub.fet.smartschool.security.services.AdminDetailsImpl;
import ub.fet.smartschool.security.services.UserDetailsImpl;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/student")
public class AuthStudentController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	StudentRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	FacultyRepository facultyRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupStudentRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		Student user = new Student();
		user.setAddress(signUpRequest.getAddress());
		user.setEmail(signUpRequest.getEmail());
		user.setNationalid(signUpRequest.getNationalid());
		user.setRealname(signUpRequest.getRealname());
		user.setUsername(signUpRequest.getUsername());
		user.setSex(signUpRequest.getSex());
		user.setDob(signUpRequest.getDob());
		user.setRegdate(LocalDateTime.now());
		user.setMatricule("FE17A035");
		user.setLevel(200);
		user.setPassword(encoder.encode(signUpRequest.getPassword()));

		Set<Role> roles = new HashSet<>();


		Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);

		user.setRoles(roles);

		if(facultyRepository.existsByFacultyName(signUpRequest.getFaculty())){
			user.setFaculty(facultyRepository.findByFacultyName(signUpRequest.getFaculty()).get());
		}
		if(departmentRepository.existsByDepartmentName(signUpRequest.getDepartment())){
			user.setDepartment(departmentRepository.findByDepartmentName(signUpRequest.getDepartment()).get());
		}
		userRepository.save(user);

		return ResponseEntity.ok(user);
	}
}
