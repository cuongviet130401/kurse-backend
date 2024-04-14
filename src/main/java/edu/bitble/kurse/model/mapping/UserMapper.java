package edu.bitble.kurse.model.mapping;

import edu.bitble.kurse.model.dto.User;
import edu.bitble.kurse.model.persistence.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toDto(UserEntity entity);

}
