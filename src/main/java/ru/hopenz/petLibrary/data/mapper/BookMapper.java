package ru.hopenz.petLibrary.data.mapper;

import org.mapstruct.Mapper;
import ru.hopenz.petLibrary.data.dto.ResponseBookDto;
import ru.hopenz.petLibrary.data.entity.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {

    ResponseBookDto toResponseDto(Book book);
}
