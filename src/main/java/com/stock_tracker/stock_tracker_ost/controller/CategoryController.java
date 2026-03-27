package com.stock_tracker.stock_tracker_ost.controller;

import com.stock_tracker.stock_tracker_ost.model.Category;
import com.stock_tracker.stock_tracker_ost.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @PostMapping
    public Category create(@RequestParam String name,
                           @RequestParam(required = false) Long parentId) {
        return categoryService.create(name, parentId);
    }
}