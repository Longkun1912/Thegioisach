package org.example.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.example.domain.BookDTO;
import org.example.domain.BookDetailsDTO;
import org.example.domain.CategoryDTO;
import org.example.domain.CommentDTO;
import org.example.domain.PostDTO;
import org.example.domain.PostHandlingDTO;
import org.example.domain.UserDTO;
import org.example.domain.UserDetailsDTO;
import org.example.domain.UserRegisterDTO;
import org.example.entity.Book;
import org.example.entity.Category;
import org.example.entity.Comment;
import org.example.entity.Favorite;
import org.example.entity.Post;
import org.example.entity.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-26T13:11:04+0700",
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
        userRegisterDTO.setImage( user.getImage() );
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
        user.setImage( userRegisteredDto.getImage() );
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
        userDTO.setImage( user.getImage() );
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
        user.setImage( userDTO.getImage() );
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
        bookDetailsDTO.setContent( book.getContent() );
        bookDetailsDTO.setRecommended_age( book.getRecommended_age() );
        bookDetailsDTO.setCategory( book.getCategory() );
        List<Favorite> list = book.getFavorites();
        if ( list != null ) {
            bookDetailsDTO.setFavorites( new ArrayList<Favorite>( list ) );
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
        book.setContent( bookDetailsDTO.getContent() );
        book.setRecommended_age( bookDetailsDTO.getRecommended_age() );
        book.setCategory( bookDetailsDTO.getCategory() );
        List<Favorite> list = bookDetailsDTO.getFavorites();
        if ( list != null ) {
            book.setFavorites( new ArrayList<Favorite>( list ) );
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
        bookDTO.setAuthor( book.getAuthor() );
        bookDTO.setPage( book.getPage() );
        bookDTO.setDescription( book.getDescription() );
        bookDTO.setRecommended_age( String.valueOf( book.getRecommended_age() ) );

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
        book.setPage( bookDTO.getPage() );
        book.setDescription( bookDTO.getDescription() );
        if ( bookDTO.getRecommended_age() != null ) {
            book.setRecommended_age( Integer.parseInt( bookDTO.getRecommended_age() ) );
        }

        return book;
    }

    @Override
    public PostDTO postDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        postDTO.setId( post.getId() );
        postDTO.setTitle( post.getTitle() );
        postDTO.setContent_text( post.getContent_text() );
        postDTO.setComments( commentListToCommentDTOList( post.getComments() ) );
        List<User> list1 = post.getSharedBy();
        if ( list1 != null ) {
            postDTO.setSharedBy( new ArrayList<User>( list1 ) );
        }

        return postDTO;
    }

    @Override
    public Post postDtoToPost(PostDTO postDTO) {
        if ( postDTO == null ) {
            return null;
        }

        Post post = new Post();

        post.setId( postDTO.getId() );
        post.setTitle( postDTO.getTitle() );
        post.setContent_text( postDTO.getContent_text() );
        post.setComments( commentDTOListToCommentList( postDTO.getComments() ) );
        List<User> list1 = postDTO.getSharedBy();
        if ( list1 != null ) {
            post.setSharedBy( new ArrayList<User>( list1 ) );
        }

        return post;
    }

    @Override
    public PostHandlingDTO postHandlingDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostHandlingDTO postHandlingDTO = new PostHandlingDTO();

        postHandlingDTO.setId( post.getId() );
        postHandlingDTO.setTitle( post.getTitle() );
        postHandlingDTO.setContent_text( post.getContent_text() );

        return postHandlingDTO;
    }

    @Override
    public Post postHandlingDtoToPost(PostHandlingDTO postHandlingDTO) {
        if ( postHandlingDTO == null ) {
            return null;
        }

        Post post = new Post();

        post.setId( postHandlingDTO.getId() );
        post.setTitle( postHandlingDTO.getTitle() );
        post.setContent_text( postHandlingDTO.getContent_text() );

        return post;
    }

    @Override
    public CommentDTO commentDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId( comment.getId() );
        commentDTO.setText( comment.getText() );
        commentDTO.setReplies( commentListToCommentDTOList( comment.getReplies() ) );
        commentDTO.setParent( commentDto( comment.getParent() ) );

        return commentDTO;
    }

    @Override
    public Comment commentDtoToComment(CommentDTO commentDTO) {
        if ( commentDTO == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setId( commentDTO.getId() );
        comment.setText( commentDTO.getText() );
        comment.setReplies( commentDTOListToCommentList( commentDTO.getReplies() ) );
        comment.setParent( commentDtoToComment( commentDTO.getParent() ) );

        return comment;
    }

    protected List<CommentDTO> commentListToCommentDTOList(List<Comment> list) {
        if ( list == null ) {
            return null;
        }

        List<CommentDTO> list1 = new ArrayList<CommentDTO>( list.size() );
        for ( Comment comment : list ) {
            list1.add( commentDto( comment ) );
        }

        return list1;
    }

    protected List<Comment> commentDTOListToCommentList(List<CommentDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Comment> list1 = new ArrayList<Comment>( list.size() );
        for ( CommentDTO commentDTO : list ) {
            list1.add( commentDtoToComment( commentDTO ) );
        }

        return list1;
    }
}
