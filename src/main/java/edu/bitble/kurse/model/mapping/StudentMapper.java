package edu.bitble.kurse.model.mapping;

import edu.bitble.kurse.model.dto.Student;
import edu.bitble.kurse.model.persistence.StudentEntity;
import edu.bitble.kurse.model.request.StudentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentEntity toPersistent(Student student);

    Student toDto(StudentEntity entity);
    Student toDto(StudentRequest req);

}
