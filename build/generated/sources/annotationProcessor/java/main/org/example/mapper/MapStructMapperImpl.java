package org.example.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.example.domain.BookDTO;
import org.example.domain.BookDetailsDTO;
import org.example.domain.CategoryDTO;
import org.example.domain.UserDTO;
import org.example.domain.UserDetailsDTO;
import org.example.domain.UserRegisterDTO;
import org.example.entity.Book;
import org.example.entity.Category;
import org.example.entity.Comment;
import org.example.entity.Dislike;
import org.example.entity.Favorite;
import org.example.entity.Like;
import org.example.entity.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-02T20:28:43+0700",
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
    public UserDTO userDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setUsername( user.getUsername() );
        byte[] image = user.getImage();
        if ( image != null ) {
            userDTO.setImage( Arrays.copyOf( image, image.length ) );
        }
        userDTO.setEmail( user.getEmail() );
        userDTO.setPhone_number( user.getPhone_number() );
        userDTO.setStatus( user.getStatus() );
        userDTO.setPassword( user.getPassword() );

        return userDTO;
    }

    @Override
    public User userDtoToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDTO.getId() );
        user.setUsername( userDTO.getUsername() );
        byte[] image = userDTO.getImage();
        if ( image != null ) {
            user.setImage( Arrays.copyOf( image, image.length ) );
        }
        user.setEmail( userDTO.getEmail() );
        user.setPhone_number( userDTO.getPhone_number() );
        user.setStatus( userDTO.getStatus() );
        user.setPassword( userDTO.getPassword() );

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

    @Override
    public CategoryDTO categoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId( category.getId() );
        categoryDTO.setName( category.getName() );

        return categoryDTO;
    }

    @Override
    public Category categoryDtoToCategory(CategoryDTO categoryDTO) {
        if ( categoryDTO == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( categoryDTO.getId() );
        category.setName( categoryDTO.getName() );

        return category;
    }

    @Override
    public BookDetailsDTO bookDetailsDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDetailsDTO bookDetailsDTO = new BookDetailsDTO();

        bookDetailsDTO.setId( book.getId() );
        bookDetailsDTO.setTitle( book.getTitle() );
        bookDetailsDTO.setAuthor( book.getAuthor() );
        bookDetailsDTO.setPublished_date( book.getPublished_date() );
        bookDetailsDTO.setPage( book.getPage() );
        bookDetailsDTO.setDescription( book.getDescription() );
        byte[] content = book.getContent();
        if ( content != null ) {
            bookDetailsDTO.setContent( Arrays.copyOf( content, content.length ) );
        }
        bookDetailsDTO.setRecommended_age( book.getRecommended_age() );
        bookDetailsDTO.setCategory( book.getCategory() );
        List<Like> list = book.getLikes();
        if ( list != null ) {
            bookDetailsDTO.setLikes( new ArrayList<Like>( list ) );
        }
        List<Dislike> list1 = book.getDislikes();
        if ( list1 != null ) {
            bookDetailsDTO.setDislikes( new ArrayList<Dislike>( list1 ) );
        }
        List<Favorite> list2 = book.getFavorites();
        if ( list2 != null ) {
            bookDetailsDTO.setFavorites( new ArrayList<Favorite>( list2 ) );
        }
        List<Comment> list3 = book.getComments();
        if ( list3 != null ) {
            bookDetailsDTO.setComments( new ArrayList<Comment>( list3 ) );
        }

        return bookDetailsDTO;
    }

    @Override
    public Book bookDetailsDtoToBook(BookDetailsDTO bookDetailsDTO) {
        if ( bookDetailsDTO == null ) {
            return null;
        }

        Book book = new Book();

        book.setId( bookDetailsDTO.getId() );
        book.setTitle( bookDetailsDTO.getTitle() );
        book.setAuthor( bookDetailsDTO.getAuthor() );
        book.setPublished_date( bookDetailsDTO.getPublished_date() );
        book.setPage( bookDetailsDTO.getPage() );
        book.setDescription( bookDetailsDTO.getDescription() );
        byte[] content = bookDetailsDTO.getContent();
        if ( content != null ) {
            book.setContent( Arrays.copyOf( content, content.length ) );
        }
        book.setRecommended_age( bookDetailsDTO.getRecommended_age() );
        book.setCategory( bookDetailsDTO.getCategory() );
        List<Comment> list = bookDetailsDTO.getComments();
        if ( list != null ) {
            book.setComments( new ArrayList<Comment>( list ) );
        }
        List<Like> list1 = bookDetailsDTO.getLikes();
        if ( list1 != null ) {
            book.setLikes( new ArrayList<Like>( list1 ) );
        }
        List<Dislike> list2 = bookDetailsDTO.getDislikes();
        if ( list2 != null ) {
            book.setDislikes( new ArrayList<Dislike>( list2 ) );
        }
        List<Favorite> list3 = bookDetailsDTO.getFavorites();
        if ( list3 != null ) {
            book.setFavorites( new ArrayList<Favorite>( list3 ) );
        }

        return book;
    }

    @Override
    public BookDTO bookDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDTO bookDTO = new BookDTO();

        bookDTO.setId( book.getId() );
        bookDTO.setTitle( book.getTitle() );
        byte[] image = book.getImage();
        if ( image != null ) {
            bookDTO.setImage( Arrays.copyOf( image, image.length ) );
        }
        bookDTO.setAuthor( book.getAuthor() );
        bookDTO.setPublished_date( book.getPublished_date() );
        bookDTO.setPage( book.getPage() );
        bookDTO.setDescription( book.getDescription() );
        byte[] content = book.getContent();
        if ( content != null ) {
            bookDTO.setContent( Arrays.copyOf( content, content.length ) );
        }
        bookDTO.setRecommended_age( book.getRecommended_age() );

        return bookDTO;
    }

    @Override
    public Book BookDtoToBook(BookDTO bookDTO) {
        if ( bookDTO == null ) {
            return null;
        }

        Book book = new Book();

        book.setId( bookDTO.getId() );
        book.setTitle( bookDTO.getTitle() );
        book.setAuthor( bookDTO.getAuthor() );
        book.setPublished_date( bookDTO.getPublished_date() );
        book.setPage( bookDTO.getPage() );
        book.setDescription( bookDTO.getDescription() );
        byte[] image = bookDTO.getImage();
        if ( image != null ) {
            book.setImage( Arrays.copyOf( image, image.length ) );
        }
        byte[] content = bookDTO.getContent();
        if ( content != null ) {
            book.setContent( Arrays.copyOf( content, content.length ) );
        }
        book.setRecommended_age( bookDTO.getRecommended_age() );

        return book;
    }
}
