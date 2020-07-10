package ub.fet.smartschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ub.fet.smartschool.model.Department;
import ub.fet.smartschool.model.Faculty;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    Optional<Department> findByDepartmentCode(String deptCode);
    Optional<Department> findByDepartmentName(String deptName);
    boolean existsByDepartmentName(String deptNmae);
    boolean existsByDepartmentCode(String deptCode);
}
