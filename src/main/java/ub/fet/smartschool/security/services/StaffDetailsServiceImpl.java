package ub.fet.smartschool.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ub.fet.smartschool.model.Staff;
import ub.fet.smartschool.model.Student;
import ub.fet.smartschool.repository.StaffRepository;
import ub.fet.smartschool.repository.StudentRepository;

@Service
public class StaffDetailsServiceImpl implements UserDetailsService {
	final
	StaffRepository staffRepository;

	public StaffDetailsServiceImpl(StaffRepository staffRepository) {
		this.staffRepository = staffRepository;
	}


	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Staff user = staffRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return StaffDetailsImpl.build(user);
	}

}
