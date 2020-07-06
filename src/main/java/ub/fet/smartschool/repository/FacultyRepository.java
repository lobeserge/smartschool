package ub.fet.smartschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ub.fet.smartschool.model.Faculty;
import ub.fet.smartschool.model.Staff;

import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {
    Optional<Faculty> findByFacultyCode(String facultyCode);
    Optional<Faculty> findByFacultyName(String facultyName);
    boolean existsByFacultyCode(String facultyCode);
    boolean existsByFacultyName(String facultyName);
}
