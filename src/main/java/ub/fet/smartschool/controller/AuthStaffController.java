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
import ub.fet.smartschool.model.Staff;
import ub.fet.smartschool.payload.request.LoginRequest;
import ub.fet.smartschool.payload.request.SignupAdminRequest;
import ub.fet.smartschool.payload.response.JwtResponse;
import ub.fet.smartschool.payload.response.MessageResponse;
import ub.fet.smartschool.repository.RoleRepository;
import ub.fet.smartschool.repository.StaffRepository;
import ub.fet.smartschool.security.jwt.JwtUtils;
import ub.fet.smartschool.security.services.AdminDetailsImpl;
import ub.fet.smartschool.security.services.StaffDetailsImpl;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/staff")
public class AuthStaffController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	StaffRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateStaff(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateStaffJwtToken(authentication);
		
		StaffDetailsImpl userDetails = (StaffDetailsImpl) authentication.getPrincipal();
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
	public ResponseEntity<?> registerStaff(@Valid @RequestBody SignupAdminRequest signUpRequest) {
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
		Staff staff = new Staff();
		staff.setAddress(signUpRequest.getAddress());
		staff.setEmail(signUpRequest.getEmail());
		staff.setM_status(signUpRequest.getM_status());
		staff.setNationalid(signUpRequest.getNationalid());
		staff.setRealnames(signUpRequest.getRealnames());
		staff.setUsername(signUpRequest.getUsername());
		staff.setSex(signUpRequest.getSex());
		staff.setDob(signUpRequest.getDob());
		staff.setRegdate(LocalDateTime.now());
		staff.setPassword(encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ROLE_STAFF)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);

						break;
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_STAFF)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			});
		}

		staff.setRoles(roles);
		userRepository.save(staff);

		return ResponseEntity.ok(staff);
	}
}
