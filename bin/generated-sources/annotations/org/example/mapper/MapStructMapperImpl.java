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
    date = "2023-04-12T00:48:38+0700",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20230218-1114, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
public class MapStructMapperImpl implements MapStructMapper {

    @Override
    public UserRegisterDTO userRegisteredDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();

        userRegisterDTO.setEmail( user.getEmail() );
        byte[] image = user.getImage();
        if ( image != null ) {
            userRegisterDTO.setImage( Arrays.copyOf( image, image.length ) );
        }
        userRegisterDTO.setPassword( user.getPassword() );
        userRegisterDTO.setPhone_number( user.getPhone_number() );
        userRegisterDTO.setUsername( user.getUsername() );

        return userRegisterDTO;
    }

    @Override
    public User userRegisteredDtoToUser(UserRegisterDTO userRegisteredDto) {
        if ( userRegisteredDto == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( userRegisteredDto.getEmail() );
        byte[] image = userRegisteredDto.getImage();
        if ( image != null ) {
            user.setImage( Arrays.copyOf( image, image.length ) );
        }
        user.setPassword( userRegisteredDto.getPassword() );
        user.setPhone_number( userRegisteredDto.getPhone_number() );
        user.setUsername( userRegisteredDto.getUsername() );

        return user;
    }

    @Override
    public UserDTO userDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setEmail( user.getEmail() );
        userDTO.setId( user.getId() );
        byte[] image = user.getImage();
        if ( image != null ) {
            userDTO.setImage( Arrays.copyOf( image, image.length ) );
        }
        userDTO.setPassword( user.getPassword() );
        userDTO.setPhone_number( user.getPhone_number() );
        userDTO.setStatus( user.getStatus() );
        userDTO.setUsername( user.getUsername() );

        return userDTO;
    }

    @Override
    public User userDtoToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( userDTO.getEmail() );
        user.setId( userDTO.getId() );
        byte[] image = userDTO.getImage();
        if ( image != null ) {
            user.setImage( Arrays.copyOf( image, image.length ) );
        }
        user.setPassword( userDTO.getPassword() );
        user.setPhone_number( userDTO.getPhone_number() );
        user.setStatus( userDTO.getStatus() );
        user.setUsername( userDTO.getUsername() );

        return user;
    }

    @Override
    public UserDetailsDTO userDetailsDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();

        userDetailsDTO.setEmail( user.getEmail() );
        userDetailsDTO.setId( user.getId() );
        if ( user.getLast_updated() != null ) {
            userDetailsDTO.setLast_updated( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( user.getLast_updated() ) );
        }
        userDetailsDTO.setPassword( user.getPassword() );
        userDetailsDTO.setPhone_number( user.getPhone_number() );
        userDetailsDTO.setStatus( user.getStatus() );
        userDetailsDTO.setUsername( user.getUsername() );

        return userDetailsDTO;
    }

    @Override
    public User userDetailsDtoToUser(UserDetailsDTO userDetailsDTO) {
        if ( userDetailsDTO == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( userDetailsDTO.getEmail() );
        user.setId( userDetailsDTO.getId() );
        if ( userDetailsDTO.getLast_updated() != null ) {
            user.setLast_updated( LocalDateTime.parse( userDetailsDTO.getLast_updated() ) );
        }
        user.setPassword( userDetailsDTO.getPassword() );
        user.setPhone_number( userDetailsDTO.getPhone_number() );
        user.setStatus( userDetailsDTO.getStatus() );
        user.setUsername( userDetailsDTO.getUsername() );

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

        bookDetailsDTO.setAuthor( book.getAuthor() );
        bookDetailsDTO.setCategory( book.getCategory() );
        List<Comment> list = book.getComments();
        if ( list != null ) {
            bookDetailsDTO.setComments( new ArrayList<Comment>( list ) );
        }
        byte[] content = book.getContent();
        if ( content != null ) {
            bookDetailsDTO.setContent( Arrays.copyOf( content, content.length ) );
        }
        bookDetailsDTO.setDescription( book.getDescription() );
        List<Dislike> list1 = book.getDislikes();
        if ( list1 != null ) {
            bookDetailsDTO.setDislikes( new ArrayList<Dislike>( list1 ) );
        }
        List<Favorite> list2 = book.getFavorites();
        if ( list2 != null ) {
            bookDetailsDTO.setFavorites( new ArrayList<Favorite>( list2 ) );
        }
        bookDetailsDTO.setId( book.getId() );
        List<Like> list3 = book.getLikes();
        if ( list3 != null ) {
            bookDetailsDTO.setLikes( new ArrayList<Like>( list3 ) );
        }
        bookDetailsDTO.setPage( book.getPage() );
        bookDetailsDTO.setPublished_date( book.getPublished_date() );
        bookDetailsDTO.setRecommended_age( book.getRecommended_age() );
        bookDetailsDTO.setTitle( book.getTitle() );

        return bookDetailsDTO;
    }

    @Override
    public Book bookDetailsDtoToBook(BookDetailsDTO bookDetailsDTO) {
        if ( bookDetailsDTO == null ) {
            return null;
        }

        Book book = new Book();

        book.setAuthor( bookDetailsDTO.getAuthor() );
        book.setCategory( bookDetailsDTO.getCategory() );
        List<Comment> list = bookDetailsDTO.getComments();
        if ( list != null ) {
            book.setComments( new ArrayList<Comment>( list ) );
        }
        byte[] content = bookDetailsDTO.getContent();
        if ( content != null ) {
            book.setContent( Arrays.copyOf( content, content.length ) );
        }
        book.setDescription( bookDetailsDTO.getDescription() );
        List<Dislike> list1 = bookDetailsDTO.getDislikes();
        if ( list1 != null ) {
            book.setDislikes( new ArrayList<Dislike>( list1 ) );
        }
        List<Favorite> list2 = bookDetailsDTO.getFavorites();
        if ( list2 != null ) {
            book.setFavorites( new ArrayList<Favorite>( list2 ) );
        }
        book.setId( bookDetailsDTO.getId() );
        List<Like> list3 = bookDetailsDTO.getLikes();
        if ( list3 != null ) {
            book.setLikes( new ArrayList<Like>( list3 ) );
        }
        book.setPage( bookDetailsDTO.getPage() );
        book.setPublished_date( bookDetailsDTO.getPublished_date() );
        book.setRecommended_age( bookDetailsDTO.getRecommended_age() );
        book.setTitle( bookDetailsDTO.getTitle() );

        return book;
    }

    @Override
    public BookDTO bookDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDTO bookDTO = new BookDTO();

        bookDTO.setAuthor( book.getAuthor() );
        bookDTO.setDescription( book.getDescription() );
        bookDTO.setId( book.getId() );
        bookDTO.setPage( book.getPage() );
        bookDTO.setRecommended_age( String.valueOf( book.getRecommended_age() ) );
        bookDTO.setTitle( book.getTitle() );

        return bookDTO;
    }

    @Override
    public Book BookDtoToBook(BookDTO bookDTO) {
        if ( bookDTO == null ) {
            return null;
        }

        Book book = new Book();

        book.setAuthor( bookDTO.getAuthor() );
        book.setDescription( bookDTO.getDescription() );
        book.setId( bookDTO.getId() );
        book.setPage( bookDTO.getPage() );
        if ( bookDTO.getRecommended_age() != null ) {
            book.setRecommended_age( Integer.parseInt( bookDTO.getRecommended_age() ) );
        }
        book.setTitle( bookDTO.getTitle() );

        return book;
    }
}
