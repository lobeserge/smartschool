package ub.fet.smartschool.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ub.fet.smartschool.model.Admin;
import ub.fet.smartschool.model.Staff;
import ub.fet.smartschool.repository.AdminRepository;
import ub.fet.smartschool.repository.StaffRepository;

@Service
public class AdminDetailsServiceImpl implements UserDetailsService {
	final
	AdminRepository adminRepository;

	public AdminDetailsServiceImpl(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}


	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin user = adminRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return AdminDetailsImpl.build(user);
	}

}
