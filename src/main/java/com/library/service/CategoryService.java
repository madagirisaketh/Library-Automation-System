package com.library.service;

import com.library.dao.CategoryDAO;
import com.library.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryDAO d;

    public CategoryService(CategoryDAO d) {
        this.d = d;
    }

    public List<Category> all() { return d.all(); }
    public void save(Category c) { d.save(c); }
    public void delete(int id) { d.delete(id); }
}