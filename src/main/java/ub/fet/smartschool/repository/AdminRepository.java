package ub.fet.smartschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ub.fet.smartschool.model.Admin;
import ub.fet.smartschool.model.Staff;

import java.util.Optional;

@Repository
public interface AdminRepository  extends JpaRepository<Admin,Long> {
    Optional<Admin> findByUsername(String username);
    Optional<Admin> findByRealnames(String username);
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
