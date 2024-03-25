package edu.uit.kurse.kursebackend.model.mapping;

import edu.uit.kurse.kursebackend.common.SecurityHandler;
import edu.uit.kurse.kursebackend.model.dto.Account;
import edu.uit.kurse.kursebackend.model.persistence.AccountPersistenceEntity;
import edu.uit.kurse.kursebackend.model.request.AccountRequestEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountPersistenceEntity toPersistence(Account req);

    Account toDto(AccountPersistenceEntity entity);

    @Named("hashPassword")
    default String hashPassword(String password) {
        return SecurityHandler.encodePassword(password);
    }

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    void updateExistingAccount(@MappingTarget AccountPersistenceEntity account, AccountRequestEntity req);



}
