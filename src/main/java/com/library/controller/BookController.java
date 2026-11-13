package com.library.controller;

import com.library.model.Book;
import com.library.service.BookService;
import com.library.service.CategoryService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookController {
	private final BookService bookService;
	private final CategoryService categoryService;

	public BookController(BookService bookService, CategoryService categoryService) {
		this.bookService = bookService;
		this.categoryService = categoryService;
	}

	@GetMapping
	public String list(Model model) {
		model.addAttribute("books", bookService.all());
		model.addAttribute("categories", categoryService.all());
		return "books";
	}

	@GetMapping("/new")
	public String addForm(Model model) {
		Book b = new Book();
		b.setStatus("ACTIVE");
		b.setTotalCopies(1);
		model.addAttribute("book", b);
		model.addAttribute("categories", categoryService.all());
		return "book-form";
	}

	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable int id, Model model) {
		Book b = bookService.find(id);
		if (b == null)
			return "redirect:/books";
		model.addAttribute("book", b);
		model.addAttribute("categories", categoryService.all());
		return "book-form";
	}

	@PostMapping("/save")
	public String save(Book book, RedirectAttributes ra) {
		boolean isNew = book.getBookId() == 0;
		bookService.save(book);
		ra.addFlashAttribute("flashOk", isNew ? "Book added to the library." : "Book updated.");
		return "redirect:/books";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes ra) {
		try {
			bookService.delete(id);
			ra.addFlashAttribute("flashOk", "Book deleted.");
		} catch (DataIntegrityViolationException e) {
			ra.addFlashAttribute("flashErr",
					"This book has borrowing history and cannot be deleted. Edit it and set the status to Inactive instead.");
		}
		return "redirect:/books";
	}
}