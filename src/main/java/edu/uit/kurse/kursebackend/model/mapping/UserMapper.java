package edu.uit.kurse.kursebackend.model.mapping;

import edu.uit.kurse.kursebackend.model.dto.User;
import edu.uit.kurse.kursebackend.model.persistence.UserPersistenceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toDto(UserPersistenceEntity entity);

}
