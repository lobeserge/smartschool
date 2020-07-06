package ub.fet.smartschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ub.fet.smartschool.model.Course;

@Repository
public interface CourseRepository  extends JpaRepository<Course,Long> {
}
