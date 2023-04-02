package org.example.mapper;

import org.example.domain.*;
import org.example.entity.Book;
import org.example.entity.Category;
import org.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    // User domain for register
    UserRegisterDTO userRegisteredDto(User user);

    @Mapping(target = "id", ignore = true)
    User userRegisteredDtoToUser(UserRegisterDTO userRegisteredDto);

    // User domain for adding and editing
    UserDTO userDto(User user);

    User userDtoToUser(UserDTO userDTO);

    // User domain for viewing
    UserDetailsDTO userDetailsDto(User user);

    User userDetailsDtoToUser(UserDetailsDTO userDetailsDTO);

    // Category domain for adding, editing and deleting
    CategoryDTO categoryDto(Category category);

    Category categoryDtoToCategory(CategoryDTO categoryDTO);

    // Book domain for admin to view, like, dislike and comment
    BookDetailsDTO bookDetailsDto(Book book);

    Book bookDetailsDtoToBook(BookDetailsDTO bookDetailsDTO);

    // Book domain for admin to add and edit
    BookDTO bookDto(Book book);

    Book BookDtoToBook(BookDTO bookDTO);
}
