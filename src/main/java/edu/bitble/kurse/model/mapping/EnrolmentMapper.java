package edu.bitble.kurse.model.mapping;

import edu.bitble.kurse.model.dto.Enrolment;
import edu.bitble.kurse.model.persistence.EnrolmentEntity;
import edu.bitble.kurse.model.request.EnrolmentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EnrolmentMapper {

    EnrolmentMapper INSTANCE = Mappers.getMapper(EnrolmentMapper.class);

	EnrolmentEntity toPersistence(EnrolmentRequest req);
    EnrolmentEntity toPersistence(Enrolment student);
    Enrolment toDto(EnrolmentEntity entity);
    Enrolment toDto(EnrolmentRequest req);

		// TODO: additional mapping

}