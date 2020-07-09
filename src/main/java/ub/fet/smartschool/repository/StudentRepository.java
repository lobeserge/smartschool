package ub.fet.smartschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ub.fet.smartschool.model.Student;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByUsername(String username);

    Optional<Student> findByMatricule(String matricule);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByMatricule(String matricule);
}
