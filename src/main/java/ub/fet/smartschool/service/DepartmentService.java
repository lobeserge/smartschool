package ub.fet.smartschool.service;

import org.springframework.stereotype.Service;
import ub.fet.smartschool.dao.DepartmentDAO;
import ub.fet.smartschool.model.Department;

@Service
public interface DepartmentService {
    public Department addDepartment(DepartmentDAO departmentDAO);
}
