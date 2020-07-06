package ub.fet.smartschool.service;

import org.springframework.stereotype.Service;
import ub.fet.smartschool.dao.FacultyDAO;
import ub.fet.smartschool.model.Faculty;

@Service
public interface FacultyService {
    public Faculty addFaculty(FacultyDAO facultyDAO);
}
