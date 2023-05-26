package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.BookDTO;
import org.example.domain.BookDetailsDTO;
import org.example.entity.Book;
import org.example.entity.Category;
import org.example.mapper.MapStructMapper;
import org.example.repository.BookRepository;
import org.example.repository.CategoryRepository;
import org.example.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final UserService userService;
    private final BookService bookService;
    private final CategoryService categoryService;
    private final MapStructMapper mapper;

    @RequestMapping(path = "/admin/book-index", method = RequestMethod.GET)
    public String bookIndex(Model model, @RequestParam(required = false) String title,
                            @RequestParam(required = false) Category category,
                            @RequestParam(required = false) String category_name,
                            @RequestParam(required = false) String author,
                            @RequestParam(required = false) Integer recommended_age,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        userService.updateModel(model);
        List<BookDetailsDTO> filtered_books;
        // Filter params
        Optional<Category> searched_category = categoryRepository.findCategoryByName(category_name);
        if(searched_category.isPresent()){
            filtered_books = bookService.getFilteredBooks(searched_category.get(),startDate,endDate,recommended_age);
        }
        else if (title != null && !title.isEmpty()){
            filtered_books = bookService.getTitleSearchedBooks(title);
        }
        else if (author != null && !author.isEmpty()){
            filtered_books = bookService.getAuthorSearchedBooks(author);
        }
        else {
            // Filter by params (default list)
            filtered_books = bookService.getFilteredBooks(category,startDate,endDate,recommended_age);
        }
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("ages", bookService.getAge());
        model.addAttribute("books",filtered_books);
        return "admin/book_index";
    }

    @RequestMapping(path = "/admin/book-details/{id}", method = RequestMethod.GET)
    public String bookDetails(@PathVariable("id") Integer book_id, Model model, HttpServletRequest request) throws FileNotFoundException {
        userService.updateModel(model);
        BookDetailsDTO book_details = bookService.getBookDetails(book_id);
        // Convert book attributes in front-end
        String book_category = book_details.getCategory().getName();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String published_date = book_details.getPublished_date().format(formatter);
        String recommended_age = Integer.toString(book_details.getRecommended_age());
        String page = Integer.toString(book_details.getPage());
        // Url for reading book content online
        File bookFile = bookService.findBookFile(book_details.getTitle());
        String contentUrl = request.getContextPath() + "/file/book/" + bookFile.getName();
        model.addAttribute("contentUrl", contentUrl);
        model.addAttribute("book",book_details);
        model.addAttribute("book_category",book_category);
        model.addAttribute("published_date",published_date);
        model.addAttribute("recommended_age",recommended_age);
        model.addAttribute("page",page);
        return "admin/book_details";
    }

    @RequestMapping(path = "/admin/add-book", method = RequestMethod.GET)
    public String addBook(Model model){
        userService.updateModel(model);
        bookService.updateBookInfoModel(model);
        model.addAttribute("book", new BookDTO());
        return "admin/add_book";
    }

    @RequestMapping(path = "/admin/add-book", method = RequestMethod.POST)
    public String addBookForm(@ModelAttribute("book") @Valid BookDTO bookDTO, BindingResult result, Model model) throws IOException {
        if(result.hasErrors()){
            userService.updateModel(model);
            bookService.updateBookInfoModel(model);
            return "admin/add_book";
        }
        else if (bookRepository.findBookByTitle(bookDTO.getTitle()).isPresent()){
            userService.updateModel(model);
            bookService.updateBookInfoModel(model);
            result.rejectValue("title",null,"Book title already exist.");
            return "admin/add_book";
        }
        else if(bookDTO.getImage_file() == null || bookDTO.getImage_file().isEmpty()){
            userService.updateModel(model);
            bookService.updateBookInfoModel(model);
            result.rejectValue("image_file",null,"Please upload the book image.");
            return "admin/add_book";
        }
        else if(!Objects.requireNonNull(bookDTO.getImage_file().getContentType()).startsWith("image/")){
            userService.updateModel(model);
            bookService.updateBookInfoModel(model);
            result.rejectValue("image_file",null,"Please upload only image files.");
            return "admin/add_book";
        }
        else if(bookDTO.getContent_file() == null || bookDTO.getContent_file().isEmpty()){
            userService.updateModel(model);
            bookService.updateBookInfoModel(model);
            result.rejectValue("content_file",null,"Please upload the book content.");
            return "admin/add_book";
        }
        else if(!Objects.requireNonNull(bookDTO.getContent_file().getContentType()).equals("application/pdf")){
            userService.updateModel(model);
            bookService.updateBookInfoModel(model);
            result.rejectValue("content_file",null,"Please upload only pdf files.");
            return "admin/add_book";
        }
        else {
            // Path to store book images
            Path image_path = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\img\\book\\" + bookService.transferBookFile(bookDTO.getImage_file(), bookDTO));
            // Path to store book contents
            Path content_path = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\file\\book\\" + bookService.transferBookFile(bookDTO.getContent_file(), bookDTO));
            // Convert date from string
            String published_date = bookDTO.getPublished_day();
            LocalDate date = LocalDate.parse(published_date.trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            // Map book entity and save to database
            Book book = mapper.BookDtoToBook(bookDTO);
            book.setCategory(categoryRepository.findCategoryByName(bookDTO.getCategory_name()).get());
            book.setRecommended_age(Integer.parseInt(bookDTO.getRecommended_age()));
            book.setPublished_date(date);
            bookRepository.save(book);
            // Save data paths to the files
            Files.write(image_path, bookDTO.getImage_file().getBytes());
            Files.write(content_path, bookDTO.getContent_file().getBytes());
            return "redirect:/admin/book-index";
        }
    }

    @RequestMapping(path = "/admin/edit-book/{id}", method = RequestMethod.GET)
    public String editBook(@PathVariable("id") Integer book_id, Model model){
        Optional<Book> book = Optional.of(bookRepository.findById(book_id).orElseThrow());
        BookDTO bookDTO = mapper.bookDto(book.get());
        userService.updateModel(model);
        bookService.updateBookInfoModel(model);
        model.addAttribute("edit_book", bookDTO);
        return "admin/edit_book";
    }

    @RequestMapping(path = "/admin/edit-book", method = RequestMethod.POST)
    public String editBookForm(@ModelAttribute("edit_book") @Valid BookDTO bookDTO, BindingResult result, Model model) throws IOException {
        if(result.hasErrors()){
            userService.updateModel(model);
            bookService.updateBookInfoModel(model);
            return "admin/edit_book";
        }
        else if (bookRepository.findBookByTitle(bookDTO.getTitle()).isPresent()){
            userService.updateModel(model);
            bookService.updateBookInfoModel(model);
            result.rejectValue("title",null,"Book title already exist.");
            return "admin/edit_book";
        }
        else if(bookDTO.getImage_file() == null || bookDTO.getImage_file().isEmpty()){
            userService.updateModel(model);
            bookService.updateBookInfoModel(model);
            result.rejectValue("image_file",null,"Please upload the book image.");
            return "admin/edit_book";
        }
        else if(!Objects.requireNonNull(bookDTO.getImage_file().getContentType()).startsWith("image/")){
            userService.updateModel(model);
            bookService.updateBookInfoModel(model);
            result.rejectValue("image_file",null,"Please upload only image files.");
            return "admin/edit_book";
        }
        else if(bookDTO.getContent_file() == null || bookDTO.getContent_file().isEmpty()){
            userService.updateModel(model);
            bookService.updateBookInfoModel(model);
            result.rejectValue("content_file",null,"Please upload the book content.");
            return "admin/edit_book";
        }
        else if(!Objects.requireNonNull(bookDTO.getContent_file().getContentType()).equals("application/pdf")){
            userService.updateModel(model);
            bookService.updateBookInfoModel(model);
            result.rejectValue("content_file",null,"Please upload only pdf files.");
            return "admin/edit_book";
        }
        else {
            // Path to store book images
            Path image_path = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\img\\book\\" + bookService.transferBookFile(bookDTO.getImage_file(), bookDTO));
            // Path to store book contents
            Path content_path = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\file\\book\\" + bookService.transferBookFile(bookDTO.getContent_file(), bookDTO));
            // Convert date from string
            String published_date = bookDTO.getPublished_day();
            LocalDate date = LocalDate.parse(published_date.trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            // Map book entity and save to database
            Optional<Book> existing_book = bookRepository.findById(bookDTO.getId());
            if(existing_book.isPresent()){
                bookService.deleteBookFilePath(existing_book.get().getTitle());
                Book book = mapper.BookDtoToBook(bookDTO);
                book.setCategory(categoryRepository.findCategoryByName(bookDTO.getCategory_name()).get());
                book.setRecommended_age(Integer.parseInt(bookDTO.getRecommended_age()));
                book.setPublished_date(date);
                bookRepository.save(book);
                // Save data paths to the files
                Files.write(image_path, bookDTO.getImage_file().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                Files.write(content_path, bookDTO.getContent_file().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
            return "redirect:/admin/book-index";
        }
    }

    @RequestMapping(path = "/admin/delete-book/{id}", method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Integer book_id){
        Optional<Book> book = Optional.of(bookRepository.findById(book_id).orElseThrow());
        bookService.deleteBookFilePath(book.get().getTitle());
        bookRepository.delete(book.get());
        return "redirect:/admin/book-index";
    }
}
