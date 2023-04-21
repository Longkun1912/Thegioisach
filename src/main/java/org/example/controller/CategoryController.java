package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.domain.CategoryDTO;
import org.example.entity.Book;
import org.example.entity.Category;
import org.example.mapper.MapStructMapper;
import org.example.repository.BookRepository;
import org.example.repository.CategoryRepository;
import org.example.service.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final UserService userService;
    private final MapStructMapper mapper;

    @RequestMapping(path = "/admin/category-index", method = RequestMethod.GET)
    public String viewCategory(Model model){
        userService.updateModel(model);
        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        model.addAttribute("categories",categories);
        return "admin/category_index";
    }

    @RequestMapping(path = "/admin/add-category", method = RequestMethod.GET)
    public String addCategory(Model model){
        userService.updateModel(model);
        model.addAttribute("category", new CategoryDTO());
        return "admin/add_category";
    }

    @RequestMapping(path = "/admin/add-category", method = RequestMethod.POST)
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

    @RequestMapping(path = "/admin/edit-category/{id}", method = RequestMethod.GET)
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

    @RequestMapping(path = "/admin/edit-category", method = RequestMethod.POST)
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

    @RequestMapping(path = "/admin/delete-category/{id}", method = RequestMethod.GET)
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
}
