package edu.bitble.kurse.model.mapping;

import edu.bitble.kurse.model.dto.CourseLikeRecord;
import edu.bitble.kurse.model.persistence.CourseLikeRecordEntity;
import edu.bitble.kurse.model.request.CourseLikeRecordRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseLikeRecordMapper {

    CourseLikeRecordMapper INSTANCE = Mappers.getMapper(CourseLikeRecordMapper.class);

    CourseLikeRecordEntity toPersistence(CourseLikeRecordRequest req);

    CourseLikeRecordEntity toPersistence(CourseLikeRecord student);

    CourseLikeRecord toDto(CourseLikeRecordEntity entity);

    CourseLikeRecord toDto(CourseLikeRecordRequest req);

    // TODO: additional mapping

}