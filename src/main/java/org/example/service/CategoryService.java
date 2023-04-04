package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Category;
import org.example.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<String> getCategories(){
        return categoryRepository.findAll().stream().map(Category::getName).toList();
    }
}
