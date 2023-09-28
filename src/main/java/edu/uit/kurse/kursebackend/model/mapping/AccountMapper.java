package edu.uit.kurse.kursebackend.model.mapping;

import edu.uit.kurse.kursebackend.common.SecurityHandler;
import edu.uit.kurse.kursebackend.model.persistent.Account;
import edu.uit.kurse.kursebackend.model.request.AccountRequestEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(source = "password", target = "password", qualifiedByName = "hashPassword")
    Account toPersistent(AccountRequestEntity req);

    @Named("hashPassword")
    default String hashPassword(String password) {
        return SecurityHandler.encodePassword(password);
    }

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    void updateExistingAccount(@MappingTarget Account account, AccountRequestEntity req);



}
