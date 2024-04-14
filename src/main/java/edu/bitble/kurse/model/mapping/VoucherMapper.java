package edu.bitble.kurse.model.mapping;

import edu.bitble.kurse.model.dto.Voucher;
import edu.bitble.kurse.model.persistence.VoucherEntity;
import edu.bitble.kurse.model.request.VoucherRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VoucherMapper {

    VoucherMapper INSTANCE = Mappers.getMapper(VoucherMapper.class);


    VoucherEntity toPersistence(VoucherRequest req);

    VoucherEntity toPersistence(Voucher student);

    Voucher toDto(VoucherEntity entity);

    Voucher toDto(VoucherRequest req);

    // TODO: additional mapping

}