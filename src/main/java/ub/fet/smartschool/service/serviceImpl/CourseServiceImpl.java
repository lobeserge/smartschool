package ub.fet.smartschool.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ub.fet.smartschool.dao.CourseDAO;
import ub.fet.smartschool.model.Course;
import ub.fet.smartschool.model.ESemester;
import ub.fet.smartschool.repository.CourseRepository;
import ub.fet.smartschool.repository.DepartmentRepository;
import ub.fet.smartschool.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public Course addCourse(CourseDAO courseDAO) {
            Course course=new Course();
            course.setCourseLevel(courseDAO.getCourseLevel());
            course.setCourseName(courseDAO.getCourseName());
            course.setCourseMarks(courseDAO.getCourseMarks());
            if(courseDAO.getSemester()==1){
                course.setSemester(ESemester.SEMESTER_ONE);
            }
            else if(courseDAO.getSemester()==2){
                course.setSemester(ESemester.SEMESTER_TWO);
            }
        if(departmentRepository.existsByDepartmentCode(courseDAO.getDepartmentCode())){
            course.setDepartment(departmentRepository.findByDepartmentCode(courseDAO.getDepartmentCode()).get());
        }

        course.setCourseMarks(courseDAO.getCourseMarks());
        course.setCourseCode(courseDAO.getCourseCode());

        courseRepository.save(course);
        return course;
    }
}
