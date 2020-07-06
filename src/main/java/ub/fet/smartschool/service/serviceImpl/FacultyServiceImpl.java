package ub.fet.smartschool.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ub.fet.smartschool.dao.FacultyDAO;
import ub.fet.smartschool.model.Faculty;
import ub.fet.smartschool.repository.FacultyRepository;
import ub.fet.smartschool.service.FacultyService;

import java.time.LocalDateTime;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    FacultyRepository facultyRepository;

    @Override
    public Faculty addFaculty(FacultyDAO facultyDAO) {
        Faculty faculty=new Faculty();
        faculty.setFacultyName(facultyDAO.getFacultyName());
        faculty.setFacultyCode(facultyDAO.getFacultyCode());
        faculty.setCreationDate(LocalDateTime.now());
        facultyRepository.save(faculty);
        return faculty;
    }
}
