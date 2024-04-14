package edu.bitble.kurse.model.mapping;

import edu.bitble.kurse.common.SecurityHandler;
import edu.bitble.kurse.model.dto.Account;
import edu.bitble.kurse.model.persistence.AccountEntity;
import edu.bitble.kurse.model.request.AccountRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountEntity toPersistent(Account account);

    Account toDto(AccountEntity entity);

    @Named("hashPassword")
    default String hashPassword(String password) {
        return SecurityHandler.encodePassword(password);
    }

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    void updateExistingAccount(@MappingTarget AccountEntity account, AccountRequest req);



}
