package com.library.service;

import com.library.dao.BookDAO;
import com.library.dao.BorrowDAO;
import com.library.model.Borrow;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowService {
    private static final int MAX_BOOKS_PER_STUDENT = 3;

    private final BorrowDAO borrowDAO;
    private final BookDAO bookDAO;

    public BorrowService(BorrowDAO borrowDAO, BookDAO bookDAO) {
        this.borrowDAO = borrowDAO;
        this.bookDAO = bookDAO;
    }

    public List<Borrow> all() { return borrowDAO.all(); }
    public List<Borrow> byStudent(int studentId) { return borrowDAO.byStudent(studentId); }

    @Transactional
    public String issue(int studentId, int bookId, String dueDate) {
        LocalDate due;
        try {
            due = LocalDate.parse(dueDate);
        } catch (Exception e) {
            return "Please choose a valid due date.";
        }
        if (due.isBefore(LocalDate.now())) return "Due date cannot be in the past.";
        if (borrowDAO.activeCountForStudent(studentId) >= MAX_BOOKS_PER_STUDENT)
            return "This student already has " + MAX_BOOKS_PER_STUDENT + " books issued. Return one first.";
        if (borrowDAO.hasActive(studentId, bookId))
            return "This student already has a copy of this book.";
        if (bookDAO.decrementAvailable(bookId) == 0)
            return "Sorry, this book is not available right now.";

        borrowDAO.insert(studentId, bookId, Date.valueOf(due));
        return null;
    }

    @Transactional
    public boolean giveBack(int borrowId) {
        List<Integer> bookIds = borrowDAO.activeBookId(borrowId);
        if (bookIds.isEmpty()) return false;
        if (borrowDAO.markReturned(borrowId) == 0) return false;
        bookDAO.incrementAvailable(bookIds.get(0));
        return true;
    }
}