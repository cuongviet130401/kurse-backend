package edu.bitble.kurse.model.mapping;

import edu.bitble.kurse.model.dto.CourseRatingRecord;
import edu.bitble.kurse.model.persistence.CourseRatingRecordEntity;
import edu.bitble.kurse.model.request.CourseRatingRecordRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseRatingRecordMapper {

    CourseRatingRecordMapper INSTANCE = Mappers.getMapper(CourseRatingRecordMapper.class);

	CourseRatingRecordEntity toPersistence(CourseRatingRecordRequest req);
    CourseRatingRecordEntity toPersistence(CourseRatingRecord student);
    CourseRatingRecord toDto(CourseRatingRecordEntity entity);
    CourseRatingRecord toDto(CourseRatingRecordRequest req);

		// TODO: additional mapping

}