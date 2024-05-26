package edu.bitble.kurse.service;

import edu.bitble.kurse.model.dto.Student;
import edu.bitble.kurse.model.mapping.StudentMapper;
import edu.bitble.kurse.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {
    
    private final StudentRepository repository;
    
    public Student getStudentDetails(Integer id) {
        return repository.findById(id)
                .map(StudentMapper.INSTANCE::toDto)
                .orElseThrow();
    }

    public Object createStudentDetails(Integer acccountId) {
        return null;
    }
}
