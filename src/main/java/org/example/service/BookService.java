package org.example.service;

import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.example.domain.BookDetailsDTO;
import org.example.domain.BookImageData;
import org.example.domain.CategoryDTO;
import org.example.entity.Book;
import org.example.entity.Category;
import org.example.mapper.MapStructMapper;
import org.example.repository.BookRepository;
import org.example.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final MapStructMapper mapper;

    public List<BookDetailsDTO> getFilteredBooks(Category category, LocalDate startDate, LocalDate endDate, Integer recommended_age){
        List<Book> filtered_books = bookRepository.filterBook(category,startDate,endDate,recommended_age);
        List<BookDetailsDTO> books = new ArrayList<>();
        return bookResults(filtered_books,books);
    }

    public List<BookDetailsDTO> getTitleSearchedBooks(String title){
        List<Book> searched_books = bookRepository.searchBookByTitle(title);
        List<BookDetailsDTO> books = new ArrayList<>();
        return bookResults(searched_books,books);
    }

    public List<BookDetailsDTO> getAuthorSearchedBooks(String author){
        List<Book> searched_books = bookRepository.searchBookByAuthor(author);
        List<BookDetailsDTO> books = new ArrayList<>();
        return bookResults(searched_books,books);
    }

    public List<BookDetailsDTO> bookResults(List<Book> books, List<BookDetailsDTO> mappedBooks){
        for (Book book : books){
            BookDetailsDTO bookDetailsDTO = mapper.bookDetailsDto(book);
            bookDetailsDTO.setBookImageData(mapBookImage(book.getImage(),book));
            mappedBooks.add(bookDetailsDTO);
        }
        return mappedBooks;
    }

    public BookImageData mapBookImage(byte[] image, Book book){
        Tika tika = new Tika();
        String mimeType = tika.detect(image);
        String base64EncodedImage = Base64.getEncoder().encodeToString(image);
        return new BookImageData(book, mimeType, base64EncodedImage);
    }
}
