package com.stock_tracker.stock_tracker_ost.service;

import com.stock_tracker.stock_tracker_ost.DataTransferObject.CategoryDTO;
import com.stock_tracker.stock_tracker_ost.exceptions.CategoryNotFoundException;
import com.stock_tracker.stock_tracker_ost.exceptions.DuplicateCategoryNameException;
import com.stock_tracker.stock_tracker_ost.model.Category;
import com.stock_tracker.stock_tracker_ost.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public CategoryDTO getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        return toDTO(category);
    }

    public List<CategoryDTO> getDictionary() {
        return categoryRepository.findAll().stream().map(this::toDTO).toList();
    }

    public Map<Long, String> getFlatDictionary() {
        Map<Long, String> dict = new LinkedHashMap<>();
        categoryRepository.findAll().forEach(c -> dict.put(c.getId(), c.getName()));
        return dict;
    }

    public Category create(String name, Long parentId) {
        Category category = new Category();
        category.setName(name);

        if (parentId != null) {
            Category parent = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new CategoryNotFoundException(parentId));
            if (categoryRepository.existsByNameIgnoreCaseAndParent(name, parent)) {
                throw new DuplicateCategoryNameException(name);
            }
            category.setParent(parent);
        } else {
            if (categoryRepository.existsByNameIgnoreCaseAndParentIsNull(name)) {
                throw new DuplicateCategoryNameException(name);
            }
        }

        return categoryRepository.save(category);
    }

    public CategoryDTO update(Long id, String name, Long parentId) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        category.setName(name);

        if (parentId != null) {
            Category parent = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new CategoryNotFoundException(parentId));
            if (categoryRepository.existsByNameIgnoreCaseAndParentAndIdNot(name, parent, id)) {
                throw new DuplicateCategoryNameException(name);
            }
            category.setParent(parent);
        } else {
            if (categoryRepository.existsByNameIgnoreCaseAndParentIsNullAndIdNot(name, id)) {
                throw new DuplicateCategoryNameException(name);
            }
            category.setParent(null);
        }

        return toDTO(categoryRepository.save(category));
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }
        categoryRepository.deleteById(id);
    }

    private CategoryDTO toDTO(Category c) {
        Long parentId = c.getParent() != null ? c.getParent().getId() : null;
        String parentName = c.getParent() != null ? c.getParent().getName() : null;
        return new CategoryDTO(c.getId(), c.getName(), parentId, parentName);
    }
}
