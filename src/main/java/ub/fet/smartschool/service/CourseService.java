package ub.fet.smartschool.service;

import org.springframework.stereotype.Service;
import ub.fet.smartschool.dao.CourseDAO;
import ub.fet.smartschool.model.Course;

@Service
public interface CourseService {
    public Course addCourse(CourseDAO courseDAO);
}
