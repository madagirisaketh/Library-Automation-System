package com.library.controller;

import com.library.model.Category;
import com.library.service.CategoryService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", service.all());
        return "categories";
    }

    @PostMapping("/save")
    public String save(Category category, RedirectAttributes ra) {
        try {
            service.save(category);
            ra.addFlashAttribute("flashOk", "Category added.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("flashErr", "That category already exists.");
        }
        return "redirect:/categories";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("flashOk", "Category deleted.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("flashErr", "This category still has books. Move or delete those books first.");
        }
        return "redirect:/categories";
    }
}