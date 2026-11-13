package com.library.dao;

import com.library.model.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class BookDAO {
	private final JdbcTemplate jdbc;

	public BookDAO(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	private static final String SELECT = "select b.BOOK_ID, b.TITLE, b.AUTHOR, b.PUBLISHER, b.DATE_PUBLISHED, b.STATUS, b.CATEGORY_ID, "
			+ "b.ISBN, b.TOTAL_COPIES, b.AVAILABLE_COPIES, c.NAME as CATEGORY_NAME "
			+ "from BOOKS b left join CATEGORY c on c.CATEGORY_ID = b.CATEGORY_ID ";

	private final RowMapper<Book> mapper = (r, n) -> {
		Book b = new Book();
		b.setBookId(r.getInt("BOOK_ID"));
		b.setTitle(r.getString("TITLE"));
		b.setAuthor(r.getString("AUTHOR"));
		b.setPublisher(r.getString("PUBLISHER"));
		Date d = r.getDate("DATE_PUBLISHED");
		b.setDatePublished(d == null ? "" : d.toString());
		b.setStatus(r.getString("STATUS"));
		b.setCategoryId(r.getInt("CATEGORY_ID"));
		b.setIsbn(r.getString("ISBN"));
		b.setTotalCopies(r.getInt("TOTAL_COPIES"));
		b.setAvailableCopies(r.getInt("AVAILABLE_COPIES"));
		b.setCategoryName(r.getString("CATEGORY_NAME"));
		return b;
	};

	public List<Book> all() {
		return jdbc.query(SELECT + "order by b.BOOK_ID desc", mapper);
	}

	public List<Book> available() {
		return jdbc.query(SELECT + "where b.STATUS = 'ACTIVE' and b.AVAILABLE_COPIES > 0 order by b.TITLE", mapper);
	}

	public Book find(int id) {
		List<Book> list = jdbc.query(SELECT + "where b.BOOK_ID = ?", mapper, id);
		return list.isEmpty() ? null : list.get(0);
	}

	public void save(Book b) {
		Date published = (b.getDatePublished() == null || b.getDatePublished().trim().isEmpty())
				? new Date(System.currentTimeMillis())
				: Date.valueOf(b.getDatePublished().trim());
		int total = Math.max(b.getTotalCopies(), 1);
		String isbn = b.getIsbn() == null ? "" : b.getIsbn().trim();
		String publisher = b.getPublisher() == null ? "" : b.getPublisher().trim();

		if (b.getBookId() == 0) {
			jdbc.update(
					"insert into BOOKS(BOOK_ID,TITLE,AUTHOR,PUBLISHER,DATE_PUBLISHED,STATUS,CATEGORY_ID,ISBN,TOTAL_COPIES,AVAILABLE_COPIES) "
							+ "values(BOOKS_SEQ.nextval,?,?,?,?,?,?,?,?,?)",
					b.getTitle().trim(), b.getAuthor().trim(), publisher, published, b.getStatus(), b.getCategoryId(),
					isbn, total, total);
		} else {
			jdbc.update(
					"update BOOKS set TITLE=?, AUTHOR=?, PUBLISHER=?, DATE_PUBLISHED=?, STATUS=?, CATEGORY_ID=?, ISBN=?, "
							+ "AVAILABLE_COPIES = greatest(AVAILABLE_COPIES + ? - TOTAL_COPIES, 0), TOTAL_COPIES = ? where BOOK_ID=?",
					b.getTitle().trim(), b.getAuthor().trim(), publisher, published, b.getStatus(), b.getCategoryId(),
					isbn, total, total, b.getBookId());
		}
	}

	public void delete(int id) {
		jdbc.update("delete from BOOKS where BOOK_ID=?", id);
	}

	public int decrementAvailable(int id) {
		return jdbc.update("update BOOKS set AVAILABLE_COPIES = AVAILABLE_COPIES - 1 "
				+ "where BOOK_ID=? and STATUS='ACTIVE' and AVAILABLE_COPIES > 0", id);
	}

	public void incrementAvailable(int id) {
		jdbc.update("update BOOKS set AVAILABLE_COPIES = least(AVAILABLE_COPIES + 1, TOTAL_COPIES) where BOOK_ID=?",
				id);
	}
}