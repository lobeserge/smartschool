package ub.fet.smartschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ub.fet.smartschool.model.Course;
import ub.fet.smartschool.model.Student;

import java.util.Optional;

@Repository
public interface CourseRepository  extends JpaRepository<Course,Long> {

    boolean existsByCourseCode(String coursecode);
    Optional<Course> findByCourseCode(String coursecode);
}
