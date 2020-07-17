package ub.fet.smartschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ub.fet.smartschool.model.AssignTeacherCourse;
import ub.fet.smartschool.model.Course;
import ub.fet.smartschool.model.RegStudentCourse;
import ub.fet.smartschool.model.Staff;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignTeacherCourseRepository extends JpaRepository<AssignTeacherCourse,Long> {

}
