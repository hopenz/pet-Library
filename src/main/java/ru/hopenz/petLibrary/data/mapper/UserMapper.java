package ru.hopenz.petLibrary.data.mapper;

import org.mapstruct.Mapper;
import ru.hopenz.petLibrary.data.dto.user.ResponseUserDto;
import ru.hopenz.petLibrary.data.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    ResponseUserDto toResponseDto(User user);
}
