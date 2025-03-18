package ru.hopenz.petLibrary.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hopenz.petLibrary.data.dto.book.ResponseBookDto;
import ru.hopenz.petLibrary.data.entity.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "booked", target = "isBooked")
    ResponseBookDto toResponseDto(Book book);
}
