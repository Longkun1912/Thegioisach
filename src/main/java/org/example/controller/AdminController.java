package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MapStructMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;

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
            addUserAttributesToModel(model);
            return "admin/dashboard";
        }
    }

    @GetMapping(value = "/user-index")
    public String userIndex(Model model){
        addUserAttributesToModel(model);
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
        updateModel(model);
        model.addAttribute("user",new UserDTO());
        return "admin/add_user";
    }

    @PostMapping(value = "/add-user")
    public String addUserForm(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult result, Model model,
                              @RequestParam(value = "file",required = false) MultipartFile file) throws IOException{
        if(result.hasErrors()){
            updateModel(model);
            return "admin/add_user";
        }
        else if (userRepository.findUserByName(userDTO.getUsername()).isPresent()) {
            updateModel(model);
            result.rejectValue("username",null,"Username already exists.");
            return "admin/add_user";
        }
        else if (userRepository.findUserByEmail(userDTO.getEmail()).isPresent()) {
            updateModel(model);
            result.rejectValue("email",null,"Email already exists.");
            return "admin/add_user";
        }
        else if (userRepository.findUserByPhoneNumber(userDTO.getPhone_number()).isPresent()) {
            updateModel(model);
            result.rejectValue("phone_number",null,"Phone number already exists.");
            return "admin/add_user";
        }
        else if(!Objects.equals(userDTO.getPassword(), userDTO.getConfirm_password())){
            updateModel(model);
            result.rejectValue("confirm_password",null,"Confirm password does not match.");
            return "admin/add_user";
        }
        else if(file == null || file.isEmpty()){
            updateModel(model);
            result.rejectValue("image",null,"Please upload your image.");
            return "admin/add_user";
        }
        else if(!Objects.requireNonNull(file.getContentType()).startsWith("image/")){
            updateModel(model);
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
            Files.write(path, file.getBytes());
            // Set the image attribute of the User object to the new filename
            user.setImage(file.getBytes());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setRole(roleRepository.findRoleByName(userDTO.getInput_role()));
            user.setLast_updated(LocalDateTime.now());
            userRepository.save(user);
            System.out.println(user);
            user = null;
            return "redirect:/admin/user-index";
        }
    }

    @GetMapping(value = "/edit-user/{id}")
    public String editUser(@PathVariable("id") UUID user_id, Model model){
        updateModel(model);
        Optional<User> existing_user = Optional.of(userRepository.findById(user_id).orElseThrow());
        UserDTO userDTO = mapper.userDto(existing_user.get());
        model.addAttribute("edit_user", userDTO);
        return "admin/edit_user";
    }

    @PostMapping(value = "/edit-user")
    public String editUserForm(@ModelAttribute("edit_user") @Valid UserDTO userDTO, BindingResult result, Model model,
                               @RequestParam(value = "file",required = false) MultipartFile file) throws IOException{
        if(result.hasErrors()){
            updateModel(model);
            return "admin/edit_user";
        }
        else if (userRepository.findUserByName(userDTO.getUsername()).isPresent()) {
            updateModel(model);
            result.rejectValue("username",null,"Username already exists.");
            return "admin/edit_user";
        }
        else if (userRepository.findUserByEmail(userDTO.getEmail()).isPresent()) {
            updateModel(model);
            result.rejectValue("email",null,"Email already exists.");
            return "admin/edit_user";
        }
        else if (userRepository.findUserByPhoneNumber(userDTO.getPhone_number()).isPresent()) {
            updateModel(model);
            result.rejectValue("phone_number",null,"Phone number already exists.");
            return "admin/edit_user";
        }
        else if(!Objects.equals(userDTO.getPassword(), userDTO.getConfirm_password())){
            updateModel(model);
            result.rejectValue("confirm_password",null,"Confirm password does not match.");
            return "admin/edit_user";
        }
        else if(file == null || file.isEmpty()){
            updateModel(model);
            result.rejectValue("image",null,"Please upload your image.");
            return "admin/edit_user";
        }
        else if(!Objects.requireNonNull(file.getContentType()).startsWith("image/")){
            updateModel(model);
            result.rejectValue("image",null,"Please upload only image files.");
            return "admin/edit_user";
        }
        else {
            Optional<User> existed_user = userRepository.findById(userDTO.getId());
            if(existed_user.isPresent()){
                User updated_user = mapper.userDtoToUser(userDTO);
                updated_user.setId(userDTO.getId());
                updated_user.setRole(roleRepository.findRoleByName(userDTO.getInput_role()));
                updated_user.setLast_updated(LocalDateTime.now());
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
                Files.write(path, file.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                // Set the image attribute of the User object to the new filename
                updated_user.setImage(file.getBytes());
                updated_user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                userRepository.save(updated_user);
            }
            return "redirect:/admin/user-index";
        }
    }

    @GetMapping("/delete-user/{id}")
    public String deleteStaff(@PathVariable("id") UUID user_id){
        Optional<User> user = Optional.of(userRepository.findById(user_id).orElseThrow());
        String username = user.get().getUsername();
        Path imagePath = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\img\\user");
        try (Stream<Path> walk = Files.walk(imagePath)) {
            Optional<Path> image = walk
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().contains(username))
                    .findFirst();
            if (image.isPresent()) {
                Files.delete(image.get());
            } else {
                System.out.println("Image not found");
            }
        } catch (IOException e) {
            System.out.println("Error.");
        }
        userRepository.delete(user.get());
        return "redirect:/admin/user-index";
    }

    @GetMapping(value = "/category-index")
    public String viewCategory(Model model){
        updateModel(model);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories",categories);
        return "admin/category_index";
    }

    @GetMapping(value = "/add-category")
    public String addCategory(Model model){
        updateModel(model);
        model.addAttribute("category", new CategoryDTO());
        return "admin/add_category";
    }

    @PostMapping(value = "/add-category")
    public String addCategoryForm(@ModelAttribute("category") @Valid CategoryDTO categoryDTO,
                                  BindingResult result, Model model){
        if(result.hasErrors()){
            updateModel(model);
            return "admin/add_category";
        }
        else if (categoryRepository.findCategoryByName(categoryDTO.getName()).isPresent()) {
            updateModel(model);
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
        updateModel(model);
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
            updateModel(model);
            return "admin/edit_category";
        }
        else if (categoryRepository.findCategoryByName(categoryDTO.getName()).isPresent()) {
            updateModel(model);
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
        updateModel(model);
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
        updateModel(model);
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
        model.addAttribute("categories", getCategories());
        model.addAttribute("ages", getAge());
        model.addAttribute("books",filtered_books);
        return "admin/book_index";
    }

    @GetMapping(value = "/add-book")
    public String addBook(Model model){
        updateModel(model);
        model.addAttribute("categories", getCategories());
        model.addAttribute("ages", getAge());
        model.addAttribute("book", new BookDTO());
        return "admin/add_book";
    }

    @PostMapping(value = "/add-book")
    public String addBookForm(@ModelAttribute("book") @Valid BookDTO bookDTO, BindingResult result, Model model,
                              @RequestParam(value = "published_date") LocalDate published_date,
                              @RequestParam(value = "book_image") MultipartFile book_image,
                              @RequestParam(value = "book_content") MultipartFile book_content) throws IOException {
        if(result.hasErrors()){
            updateModel(model);
            return "admin/add_book";
        }
        else if(book_image == null || book_image.isEmpty()){
            updateModel(model);
            result.rejectValue("image",null,"Please upload the book image.");
            return "admin/add_book";
        }
        else if(!Objects.requireNonNull(book_image.getContentType()).startsWith("image/")){
            updateModel(model);
            result.rejectValue("image",null,"Please upload only image files.");
            return "admin/add_book";
        }
        else if(book_content == null || book_content.isEmpty()){
            updateModel(model);
            result.rejectValue("content",null,"Please upload the book content.");
            return "admin/add_book";
        }
        else if(!Objects.requireNonNull(book_content.getContentType()).equals("application/pdf")){
            updateModel(model);
            result.rejectValue("content",null,"Please upload only pdf files.");
            return "admin/add_book";
        }
        else {
            Book book = mapper.BookDtoToBook(bookDTO);
            // Path to store book images
            Path image_path = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\img\\book\\" + transferBookFile(book_image, bookDTO));
            // Path to store book contents
            Path content_path = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\file\\book\\" + transferBookFile(book_content, bookDTO));
            // Save data paths to the files
            Files.write(image_path, book_image.getBytes());
            Files.write(content_path, book_content.getBytes());
            book.setImage(book_image.getBytes());
            book.setContent(book_content.getBytes());
            book.setCategory(categoryRepository.findCategoryByName(bookDTO.getCategory_name()).get());
            book.setPublished_date(published_date);
            bookRepository.save(book);
            return "redirect:/admin/book-index";
        }
    }

    public String transferBookFile(MultipartFile file, BookDTO bookDTO){
        String originalFilename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename);
        return bookDTO.getTitle() + "." + extension;
    }

    private List<String> getCategories(){
        return categoryRepository.findAll().stream().map(Category::getName).toList();
    }

    private List<Integer> getAge(){
        List<Integer> ages = new ArrayList<>();
        ages.add(6);
        ages.add(12);
        ages.add(16);
        return ages;
    }

    private List<String> getStatus(){
        List<String> status = new ArrayList<>();
        status.add("Enabled");
        status.add("Disabled");
        return status;
    }

    private List<String> getRole(){
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    private void updateModel(Model model){
        addUserAttributesToModel(model);
        model.addAttribute("status",getStatus());
        model.addAttribute("roles", getRole());
    }

    public void addUserAttributesToModel(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(auth.getName()).get();
        String role = user.getRole().getName();
        model.addAttribute("user_detail", user);
        model.addAttribute("role", role);
    }
}
