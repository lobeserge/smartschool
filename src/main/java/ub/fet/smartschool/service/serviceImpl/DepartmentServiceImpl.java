package ub.fet.smartschool.service.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ub.fet.smartschool.dao.DepartmentDAO;
import ub.fet.smartschool.model.Department;
import ub.fet.smartschool.repository.DepartmentRepository;
import ub.fet.smartschool.repository.FacultyRepository;
import ub.fet.smartschool.service.DepartmentService;

import java.time.LocalDateTime;

@Service
public class DepartmentServiceImpl  implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private FacultyRepository facultyRepository;

    @Override
    public Department addDepartment(DepartmentDAO departmentDAO) {
        Department department = new Department();
        department.setDepartmentName(departmentDAO.getDepartmentName());
        department.setDepartmentCode(departmentDAO.getDepartmentCode());
        department.setCreationDate(LocalDateTime.now());
        if(facultyRepository.existsByFacultyCode(departmentDAO.getFacultyCode())){
            department.setFaculty(facultyRepository.findByFacultyCode(departmentDAO.getFacultyCode()).get());
        }
        else{
            return null;
        }
        departmentRepository.save(department);
        return department;
    }
}
