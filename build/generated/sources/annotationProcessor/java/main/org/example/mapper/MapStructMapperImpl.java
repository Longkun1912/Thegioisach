package org.example.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import javax.annotation.processing.Generated;
import org.example.domain.UserAddingDTO;
import org.example.domain.UserDetailsDTO;
import org.example.domain.UserRegisterDTO;
import org.example.entity.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-27T20:40:33+0700",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.jar, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class MapStructMapperImpl implements MapStructMapper {

    @Override
    public UserRegisterDTO userRegisteredDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();

        userRegisterDTO.setUsername( user.getUsername() );
        byte[] image = user.getImage();
        if ( image != null ) {
            userRegisterDTO.setImage( Arrays.copyOf( image, image.length ) );
        }
        userRegisterDTO.setEmail( user.getEmail() );
        userRegisterDTO.setPhone_number( user.getPhone_number() );
        userRegisterDTO.setPassword( user.getPassword() );

        return userRegisterDTO;
    }

    @Override
    public User userRegisteredDtoToUser(UserRegisterDTO userRegisteredDto) {
        if ( userRegisteredDto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userRegisteredDto.getUsername() );
        byte[] image = userRegisteredDto.getImage();
        if ( image != null ) {
            user.setImage( Arrays.copyOf( image, image.length ) );
        }
        user.setEmail( userRegisteredDto.getEmail() );
        user.setPhone_number( userRegisteredDto.getPhone_number() );
        user.setPassword( userRegisteredDto.getPassword() );

        return user;
    }

    @Override
    public UserAddingDTO userAddingDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserAddingDTO userAddingDTO = new UserAddingDTO();

        userAddingDTO.setUsername( user.getUsername() );
        byte[] image = user.getImage();
        if ( image != null ) {
            userAddingDTO.setImage( Arrays.copyOf( image, image.length ) );
        }
        userAddingDTO.setEmail( user.getEmail() );
        userAddingDTO.setPhone_number( user.getPhone_number() );
        userAddingDTO.setStatus( user.getStatus() );
        userAddingDTO.setPassword( user.getPassword() );
        userAddingDTO.setRole( user.getRole() );

        return userAddingDTO;
    }

    @Override
    public User userAddingDtoToUser(UserAddingDTO userAddingDTO) {
        if ( userAddingDTO == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userAddingDTO.getUsername() );
        byte[] image = userAddingDTO.getImage();
        if ( image != null ) {
            user.setImage( Arrays.copyOf( image, image.length ) );
        }
        user.setEmail( userAddingDTO.getEmail() );
        user.setPhone_number( userAddingDTO.getPhone_number() );
        user.setStatus( userAddingDTO.getStatus() );
        user.setPassword( userAddingDTO.getPassword() );
        user.setRole( userAddingDTO.getRole() );

        return user;
    }

    @Override
    public UserDetailsDTO userDetailsDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();

        userDetailsDTO.setId( user.getId() );
        userDetailsDTO.setUsername( user.getUsername() );
        userDetailsDTO.setEmail( user.getEmail() );
        userDetailsDTO.setPhone_number( user.getPhone_number() );
        userDetailsDTO.setStatus( user.getStatus() );
        if ( user.getLast_updated() != null ) {
            userDetailsDTO.setLast_updated( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( user.getLast_updated() ) );
        }
        userDetailsDTO.setPassword( user.getPassword() );

        return userDetailsDTO;
    }

    @Override
    public User userDetailsDtoToUser(UserDetailsDTO userDetailsDTO) {
        if ( userDetailsDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDetailsDTO.getId() );
        user.setUsername( userDetailsDTO.getUsername() );
        user.setEmail( userDetailsDTO.getEmail() );
        user.setPhone_number( userDetailsDTO.getPhone_number() );
        user.setStatus( userDetailsDTO.getStatus() );
        if ( userDetailsDTO.getLast_updated() != null ) {
            user.setLast_updated( LocalDateTime.parse( userDetailsDTO.getLast_updated() ) );
        }
        user.setPassword( userDetailsDTO.getPassword() );

        return user;
    }
}
