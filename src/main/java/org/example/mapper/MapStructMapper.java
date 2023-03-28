package org.example.mapper;

import org.example.domain.UserAddingDTO;
import org.example.domain.UserDetailsDTO;
import org.example.domain.UserRegisterDTO;
import org.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    UserRegisterDTO userRegisteredDto(User user);

    @Mapping(target = "id", ignore = true)
    User userRegisteredDtoToUser(UserRegisterDTO userRegisteredDto);

    UserAddingDTO userAddingDto(User user);

    @Mapping(target = "id", ignore = true)
    User userAddingDtoToUser(UserAddingDTO userAddingDTO);

    UserDetailsDTO userDetailsDto(User user);

    User userDetailsDtoToUser(UserDetailsDTO userDetailsDTO);
}
