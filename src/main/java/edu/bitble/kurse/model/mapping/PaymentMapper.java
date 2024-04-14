package edu.bitble.kurse.model.mapping;

import edu.bitble.kurse.model.dto.Payment;
import edu.bitble.kurse.model.persistence.PaymentEntity;
import edu.bitble.kurse.model.request.PaymentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);


    PaymentEntity toPersistence(PaymentRequest req);

    PaymentEntity toPersistence(Payment student);

    Payment toDto(PaymentEntity entity);

    Payment toDto(PaymentRequest req);

    // TODO: additional mapping

}