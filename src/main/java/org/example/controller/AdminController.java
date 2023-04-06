package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.apache.tika.metadata.HttpHeaders;
import org.example.domain.*;
import org.example.entity.Book;
import org.example.entity.Category;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.mapper.MapStructMapper;
import org.example.repository.BookRepository;
import org.example.repository.CategoryRepository;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.example.service.BookService;
import org.example.service.CategoryService;
import org.example.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.core.io.Resource;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final UserService userService;
    private final BookService bookService;
    private final CategoryService categoryService;
    private final MapStructMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/dashboard")
    public String register(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: "+auth);

        String access_role = auth.getAuthorities().toString();
        System.out.println("Access role: "+access_role);

        if(!access_role.contains("admin")){
            return "forbidden_page";
        }
        else {
            userService.addUserAttributesToModel(model);
            return "admin/dashboard";
        }
    }

    @GetMapping(value = "/user-index")
    public String userIndex(Model model){
        userService.addUserAttributesToModel(model);
        Role role = roleRepository.findRoleByName("user");
        List<User> users = userRepository.findUserByRole(role);
        List<UserDetailsDTO> userDetailsDTOS = new ArrayList<>();
        for (User user : users){
            byte[] userImage = user.getImage();
            Tika tika = new Tika();
            String mimeType = tika.detect(userImage);
            String base64EncodedImage = Base64.getEncoder().encodeToString(userImage);
            UserImageData userImageData = new UserImageData(user, mimeType, base64EncodedImage);
            UserDetailsDTO userDetailsDTO = mapper.userDetailsDto(user);
            userDetailsDTO.setUserImageData(userImageData);
            LocalDateTime last_updated = user.getLast_updated();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy 'at' hh:mm a");
            userDetailsDTO.setLast_updated(last_updated.format(formatter));
            userDetailsDTOS.add(userDetailsDTO);
        }
        model.addAttribute("users",userDetailsDTOS);
        return "admin/user_index";
    }

    @GetMapping(value = "/add-user")
    public String addUser(Model model){
        userService.updateModel(model);
        model.addAttribute("user",new UserDTO());
        return "admin/add_user";
    }

    @PostMapping(value = "/add-user")
    public String addUserForm(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult result, Model model,
                              @RequestParam(value = "file",required = false) MultipartFile file) throws IOException{
        if(result.hasErrors()){
            userService.updateModel(model);
            return "admin/add_user";
        }
        else if (userRepository.findUserByName(userDTO.getUsername()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("username",null,"Username already exists.");
            return "admin/add_user";
        }
        else if (userRepository.findUserByEmail(userDTO.getEmail()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("email",null,"Email already exists.");
            return "admin/add_user";
        }
        else if (userRepository.findUserByPhoneNumber(userDTO.getPhone_number()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("phone_number",null,"Phone number already exists.");
            return "admin/add_user";
        }
        else if(!Objects.equals(userDTO.getPassword(), userDTO.getConfirm_password())){
            userService.updateModel(model);
            result.rejectValue("confirm_password",null,"Confirm password does not match.");
            return "admin/add_user";
        }
        else if(file == null || file.isEmpty()){
            userService.updateModel(model);
            result.rejectValue("image",null,"Please upload your image.");
            return "admin/add_user";
        }
        else if(!Objects.requireNonNull(file.getContentType()).startsWith("image/")){
            userService.updateModel(model);
            result.rejectValue("image",null,"Please upload only image files.");
            return "admin/add_user";
        }
        else {
            User user = mapper.userDtoToUser(userDTO);
            user.setId(UUID.randomUUID());
            // Get the original filename of the uploaded file
            String originalFilename = file.getOriginalFilename();
            // Extract the file extension
            String extension = FilenameUtils.getExtension(originalFilename);
            // Construct a new filename based on the user's username
            String newFilename = userDTO.getUsername() + "." + extension;
            // Save the uploaded file to the specified directory
            Path path = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\img\\user\\" + newFilename);
            if (userDTO.getInput_role().contains("admin")){
                path = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\img\\admin\\" + newFilename);
            }
            // Set the image attribute of the User object to the new filename
            user.setImage(file.getBytes());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setRole(roleRepository.findRoleByName(userDTO.getInput_role()));
            user.setLast_updated(LocalDateTime.now());
            userRepository.save(user);
            Files.write(path, file.getBytes());
            return "redirect:/admin/user-index";
        }
    }

    @GetMapping(value = "/edit-user/{id}")
    public String editUser(@PathVariable("id") UUID user_id, Model model){
        userService.updateModel(model);
        Optional<User> existing_user = Optional.of(userRepository.findById(user_id).orElseThrow());
        UserDTO userDTO = mapper.userDto(existing_user.get());
        model.addAttribute("edit_user", userDTO);
        return "admin/edit_user";
    }

    @PostMapping(value = "/edit-user")
    public String editUserForm(@ModelAttribute("edit_user") @Valid UserDTO userDTO, BindingResult result, Model model,
                               @RequestParam(value = "file",required = false) MultipartFile file) throws IOException{
        if(result.hasErrors()){
            userService.updateModel(model);
            return "admin/edit_user";
        }
        else if (userRepository.findUserByName(userDTO.getUsername()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("username",null,"Username already exists.");
            return "admin/edit_user";
        }
        else if (userRepository.findUserByEmail(userDTO.getEmail()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("email",null,"Email already exists.");
            return "admin/edit_user";
        }
        else if (userRepository.findUserByPhoneNumber(userDTO.getPhone_number()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("phone_number",null,"Phone number already exists.");
            return "admin/edit_user";
        }
        else if(!Objects.equals(userDTO.getPassword(), userDTO.getConfirm_password())){
            userService.updateModel(model);
            result.rejectValue("confirm_password",null,"Confirm password does not match.");
            return "admin/edit_user";
        }
        else if(file == null || file.isEmpty()){
            userService.updateModel(model);
            result.rejectValue("image",null,"Please upload your image.");
            return "admin/edit_user";
        }
        else if(!Objects.requireNonNull(file.getContentType()).startsWith("image/")){
            userService.updateModel(model);
            result.rejectValue("image",null,"Please upload only image files.");
            return "admin/edit_user";
        }
        else {
            Optional<User> existed_user = userRepository.findById(userDTO.getId());
            if(existed_user.isPresent()){
                // Delete user old image from file path
                userService.deleteUserImagePath(existed_user.get().getUsername());
                // Get the original filename of the uploaded file
                String originalFilename = file.getOriginalFilename();
                // Extract the file extension
                String extension = FilenameUtils.getExtension(originalFilename);
                // Construct a new filename based on the user's username
                String newFilename = userDTO.getUsername() + "." + extension;
                // Save the uploaded file to the specified directory
                Path path = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\img\\user\\" + newFilename);
                if (userDTO.getInput_role().contains("admin")){
                    path = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\img\\admin\\" + newFilename);
                }
                // Map User entity and save to database
                User updated_user = mapper.userDtoToUser(userDTO);
                updated_user.setId(userDTO.getId());
                updated_user.setRole(roleRepository.findRoleByName(userDTO.getInput_role()));
                updated_user.setLast_updated(LocalDateTime.now());
                // Set the image attribute of the User object to the new filename
                updated_user.setImage(file.getBytes());
                updated_user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                userRepository.save(updated_user);
                Files.write(path, file.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
            return "redirect:/admin/user-index";
        }
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") UUID user_id){
        Optional<User> user = Optional.of(userRepository.findById(user_id).orElseThrow());
        userService.deleteUserImagePath(user.get().getUsername());
        userRepository.delete(user.get());
        return "redirect:/admin/user-index";
    }

    @GetMapping(value = "/category-index")
    public String viewCategory(Model model){
        userService.updateModel(model);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories",categories);
        return "admin/category_index";
    }

    @GetMapping(value = "/add-category")
    public String addCategory(Model model){
        userService.updateModel(model);
        model.addAttribute("category", new CategoryDTO());
        return "admin/add_category";
    }

    @PostMapping(value = "/add-category")
    public String addCategoryForm(@ModelAttribute("category") @Valid CategoryDTO categoryDTO,
                                  BindingResult result, Model model){
        if(result.hasErrors()){
            userService.updateModel(model);
            return "admin/add_category";
        }
        else if (categoryRepository.findCategoryByName(categoryDTO.getName()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("name",null,"Category name already exists.");
            return "admin/add_category";
        }
        else {
            Category category = mapper.categoryDtoToCategory(categoryDTO);
            categoryRepository.save(category);
            return "redirect:/admin/category-index";
        }
    }

    @GetMapping("/edit-category/{id}")
    public String editCategory(@PathVariable("id") int category_id, Model model, RedirectAttributes redirectAttributes){
        userService.updateModel(model);
        Optional<Category> category = Optional.of(categoryRepository.findById(category_id).orElseThrow());
        String category_name = category.get().getName();
        System.out.println("Category: " + category_name);
        List<Book> books = bookRepository.findBookByCategory(category.get());
        if(books.size() == 0){
            System.out.println("The books of this category is empty.");
            CategoryDTO categoryDTO = mapper.categoryDto(category.get());
            model.addAttribute("edit_category",categoryDTO);
            return "admin/edit_category";
        }
        else {
            System.out.println("The book size: " + books.size());
            redirectAttributes.addFlashAttribute("message","Category " + category_name + " contains its books so it is unable to be edited.");
            return "redirect:/admin/category-index";
        }
    }

    @PostMapping("/edit-category")
    public String editCategoryForm(@ModelAttribute("edit_category") @Valid CategoryDTO categoryDTO,
                                   BindingResult result, Model model){
        if(result.hasErrors()){
            userService.updateModel(model);
            return "admin/edit_category";
        }
        else if (categoryRepository.findCategoryByName(categoryDTO.getName()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("name",null,"Category name already exists.");
            return "admin/edit_category";
        }
        else{
            Category category = mapper.categoryDtoToCategory(categoryDTO);
            categoryRepository.save(category);
            return "redirect:/admin/category-index";
        }
    }

    @GetMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable("id") int category_id, Model model, RedirectAttributes redirectAttributes){
        userService.updateModel(model);
        Optional<Category> category = Optional.of(categoryRepository.findById(category_id).orElseThrow());
        String category_name = category.get().getName();
        System.out.println("Category: " + category_name);
        List<Book> books = bookRepository.findBookByCategory(category.get());
        if(books.size() == 0){
            System.out.println("The books of this category is empty.");
            categoryRepository.delete(category.get());
        }
        else {
            System.out.println("The book size: " + books.size());
            redirectAttributes.addFlashAttribute("message","Category " + category_name + " contains its books so it is unable to be deleted.");
        }
        return "redirect:/admin/category-index";
    }

    @GetMapping("/book-index")
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

    @GetMapping("/book-details/{id}")
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

    @GetMapping(value = "/add-book")
    public String addBook(Model model){
        userService.updateModel(model);
        bookService.updateBookInfoModel(model);
        model.addAttribute("book", new BookDTO());
        return "admin/add_book";
    }

    @PostMapping(value = "/add-book")
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
            book.setImage(bookDTO.getImage_file().getBytes());
            book.setContent(bookDTO.getContent_file().getBytes());
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

    @GetMapping(value = "/edit-book/{id}")
    public String editBook(@PathVariable("id") Integer book_id, Model model){
        Optional<Book> book = Optional.of(bookRepository.findById(book_id).orElseThrow());
        BookDTO bookDTO = mapper.bookDto(book.get());
        userService.updateModel(model);
        bookService.updateBookInfoModel(model);
        model.addAttribute("edit_book", bookDTO);
        return "admin/edit_book";
    }

    @PostMapping(value = "/edit-book")
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
                book.setImage(bookDTO.getImage_file().getBytes());
                book.setContent(bookDTO.getContent_file().getBytes());
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

    @GetMapping("/delete-book/{id}")
    public String deleteBook(@PathVariable("id") Integer book_id){
        Optional<Book> book = Optional.of(bookRepository.findById(book_id).orElseThrow());
        bookService.deleteBookFilePath(book.get().getTitle());
        bookRepository.delete(book.get());
        return "redirect:/admin/book-index";
    }
}
