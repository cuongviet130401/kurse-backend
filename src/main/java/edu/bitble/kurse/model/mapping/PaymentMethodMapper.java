package edu.bitble.kurse.model.mapping;

import edu.bitble.kurse.model.dto.PaymentMethod;
import edu.bitble.kurse.model.persistence.PaymentMethodEntity;
import edu.bitble.kurse.model.request.PaymentMethodRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMethodMapper {

    PaymentMethodMapper INSTANCE = Mappers.getMapper(PaymentMethodMapper.class);

	PaymentMethodEntity toPersistence(PaymentMethodRequest req);
    PaymentMethodEntity toPersistence(PaymentMethod student);
    PaymentMethod toDto(PaymentMethodEntity entity);
    PaymentMethod toDto(PaymentMethodRequest req);

		// TODO: additional mapping

}