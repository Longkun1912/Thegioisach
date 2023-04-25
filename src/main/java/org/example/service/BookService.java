package org.example.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.example.domain.BookDTO;
import org.example.domain.BookDetailsDTO;
import org.example.domain.BookImageData;
import org.example.entity.Book;
import org.example.entity.Category;
import org.example.mapper.MapStructMapper;
import org.example.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final MapStructMapper mapper;
    private final FileService fileService;

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

    public BookDetailsDTO getBookDetails(Integer book_id){
        Optional<Book> book = Optional.of(bookRepository.findById(book_id).orElseThrow());
        BookDetailsDTO bookDetailsDTO = mapper.bookDetailsDto(book.get());
        bookDetailsDTO.setBookImageData(mapBookImage(book.get().getImage(), book.get()));
        return bookDetailsDTO;
    }

    // Find selected book file in its directory
    public File findBookFile(String title) throws FileNotFoundException{
        File directory = new File("D:\\thegioisach\\src\\main\\resources\\static\\file\\book\\");
        File[] files = directory.listFiles();
        for (File file : Objects.requireNonNull(files)){
            if(file.getName().equalsIgnoreCase(title + ".pdf")){
                System.out.println("Find founded: " + file.getName());
                return file;
            }
        }
        throw new FileNotFoundException("File not found");
    }

    public BookImageData mapBookImage(byte[] image, Book book){
        Tika tika = new Tika();
        String mimeType = tika.detect(image);
        String base64EncodedImage = Base64.getEncoder().encodeToString(image);
        return new BookImageData(book, mimeType, base64EncodedImage);
    }

    public String transferBookFile(MultipartFile file, BookDTO bookDTO){
        String originalFilename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename);
        return bookDTO.getTitle() + "." + extension;
    }

    public List<String> getAgeToString(List<Integer> ages){
        return ages.stream().map(Objects::toString).collect(Collectors.toList());
    }

    public List<Integer> getAge(){
        List<Integer> ages = new ArrayList<>();
        ages.add(6);
        ages.add(12);
        ages.add(16);
        return ages;
    }

    public void updateBookInfoModel(Model model){
        List<String> ages = getAgeToString(getAge());
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("ages", ages);
    }

    public void deleteBookFilePath(String title) {
        Path imagePath = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\img\\book\\");
        Path filePath = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\file\\book\\");
        fileService.deleteFileIfExists(imagePath,title);
        fileService.deleteFileIfExists(filePath,title);
    }
}
