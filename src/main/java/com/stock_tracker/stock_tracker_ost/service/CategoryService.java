package com.stock_tracker.stock_tracker_ost.service;

import com.stock_tracker.stock_tracker_ost.model.Category;
import com.stock_tracker.stock_tracker_ost.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category create(String name, Long parentId) {

        Category category = new Category();
        category.setName(name);

        if (parentId != null) {
            Category parent = categoryRepository.findById(parentId).orElseThrow();
            category.setParent(parent);
        }

        return categoryRepository.save(category);
    }
}