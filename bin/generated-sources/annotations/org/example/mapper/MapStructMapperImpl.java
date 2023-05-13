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
    date = "2023-05-13T13:49:57+0700",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230413-0857, environment: Java 18.0.1.1 (Oracle Corporation)"
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
        byte[] content = book.getContent();
        if ( content != null ) {
            bookDetailsDTO.setContent( Arrays.copyOf( content, content.length ) );
        }
        bookDetailsDTO.setDescription( book.getDescription() );
        List<Favorite> list = book.getFavorites();
        if ( list != null ) {
            bookDetailsDTO.setFavorites( new ArrayList<Favorite>( list ) );
        }
        bookDetailsDTO.setId( book.getId() );
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
        byte[] content = bookDetailsDTO.getContent();
        if ( content != null ) {
            book.setContent( Arrays.copyOf( content, content.length ) );
        }
        book.setDescription( bookDetailsDTO.getDescription() );
        List<Favorite> list = bookDetailsDTO.getFavorites();
        if ( list != null ) {
            book.setFavorites( new ArrayList<Favorite>( list ) );
        }
        book.setId( bookDetailsDTO.getId() );
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

    @Override
    public PostDTO postDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        postDTO.setComments( commentListToCommentDTOList( post.getComments() ) );
        postDTO.setContent_text( post.getContent_text() );
        postDTO.setId( post.getId() );
        List<User> list1 = post.getSharedBy();
        if ( list1 != null ) {
            postDTO.setSharedBy( new ArrayList<User>( list1 ) );
        }
        postDTO.setTitle( post.getTitle() );

        return postDTO;
    }

    @Override
    public Post postDtoToPost(PostDTO postDTO) {
        if ( postDTO == null ) {
            return null;
        }

        Post post = new Post();

        post.setComments( commentDTOListToCommentList( postDTO.getComments() ) );
        post.setContent_text( postDTO.getContent_text() );
        post.setId( postDTO.getId() );
        List<User> list1 = postDTO.getSharedBy();
        if ( list1 != null ) {
            post.setSharedBy( new ArrayList<User>( list1 ) );
        }
        post.setTitle( postDTO.getTitle() );

        return post;
    }

    @Override
    public PostHandlingDTO postHandlingDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostHandlingDTO postHandlingDTO = new PostHandlingDTO();

        postHandlingDTO.setContent_text( post.getContent_text() );
        postHandlingDTO.setId( post.getId() );
        postHandlingDTO.setTitle( post.getTitle() );

        return postHandlingDTO;
    }

    @Override
    public Post postHandlingDtoToPost(PostHandlingDTO postHandlingDTO) {
        if ( postHandlingDTO == null ) {
            return null;
        }

        Post post = new Post();

        post.setContent_text( postHandlingDTO.getContent_text() );
        post.setId( postHandlingDTO.getId() );
        post.setTitle( postHandlingDTO.getTitle() );

        return post;
    }

    @Override
    public CommentDTO commentDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId( comment.getId() );
        commentDTO.setParent( commentDto( comment.getParent() ) );
        commentDTO.setReplies( commentListToCommentDTOList( comment.getReplies() ) );
        commentDTO.setText( comment.getText() );

        return commentDTO;
    }

    @Override
    public Comment commentDtoToComment(CommentDTO commentDTO) {
        if ( commentDTO == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setId( commentDTO.getId() );
        comment.setParent( commentDtoToComment( commentDTO.getParent() ) );
        comment.setReplies( commentDTOListToCommentList( commentDTO.getReplies() ) );
        comment.setText( commentDTO.getText() );

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
