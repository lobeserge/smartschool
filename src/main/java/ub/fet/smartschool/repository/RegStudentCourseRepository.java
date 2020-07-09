package ub.fet.smartschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ub.fet.smartschool.model.RegStudentCourse;

@Repository
public interface RegStudentCourseRepository extends JpaRepository<RegStudentCourse,Long> {
}
