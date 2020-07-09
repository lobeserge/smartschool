package ub.fet.smartschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ub.fet.smartschool.model.Fee;
import ub.fet.smartschool.model.Student;

import java.util.Optional;

@Repository
public interface FeeRepository extends JpaRepository<Fee,Long> {

    boolean existsByStudent(Student student);
    Optional<Fee> findByStudent(Student student);
}
