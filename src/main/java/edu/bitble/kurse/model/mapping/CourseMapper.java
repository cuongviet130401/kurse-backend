package edu.bitble.kurse.model.mapping;

import edu.bitble.kurse.model.dto.Course;
import edu.bitble.kurse.model.persistence.CourseEntity;
import edu.bitble.kurse.model.request.CourseRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    CourseEntity toPersistence(CourseRequest req);

    CourseEntity toPersistence(Course student);

    Course toDto(CourseEntity entity);

    Course toDto(CourseRequest req);

    // TODO: additional mapping

}