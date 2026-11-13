package com.library.service;

import com.library.dao.BookDAO;
import com.library.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookDAO d;

    public BookService(BookDAO d) {
        this.d = d;
    }

    public List<Book> all() { return d.all(); }
    public List<Book> available() { return d.available(); }
    public Book find(int id) { return d.find(id); }
    public void save(Book b) { d.save(b); }
    public void delete(int id) { d.delete(id); }
}