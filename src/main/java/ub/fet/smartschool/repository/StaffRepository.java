package ub.fet.smartschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ub.fet.smartschool.model.Staff;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByUsername(String username);

    Optional<Staff> findByRealnames(String username);

    boolean existsByUsername(String username);

    boolean existsByRealnames(String username);

    boolean existsByEmail(String email);
}
