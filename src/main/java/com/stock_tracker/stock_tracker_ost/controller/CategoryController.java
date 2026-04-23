package com.stock_tracker.stock_tracker_ost.controller;

import com.stock_tracker.stock_tracker_ost.DataTransferObject.CategoryDTO;
import com.stock_tracker.stock_tracker_ost.model.Category;
import com.stock_tracker.stock_tracker_ost.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/dictionary")
    public List<CategoryDTO> getDictionary() {
        return categoryService.getDictionary();
    }

    @GetMapping("/flat")
    public Map<Long, String> getFlatDictionary() {
        return categoryService.getFlatDictionary();
    }

    @GetMapping("/{id}")
    public CategoryDTO getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@RequestParam String name,
                           @RequestParam(required = false) Long parentId) {
        return categoryService.create(name, parentId);
    }

    @PutMapping("/{id}")
    public CategoryDTO update(@PathVariable Long id,
                              @RequestParam String name,
                              @RequestParam(required = false) Long parentId) {
        return categoryService.update(id, name, parentId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
