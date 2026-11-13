package com.library.dao;

import com.library.model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDAO {
    private final JdbcTemplate j;

    public CategoryDAO(JdbcTemplate j) {
        this.j = j;
    }

    public List<Category> all() {
        return j.query(
            "select c.CATEGORY_ID, c.NAME, " +
            "(select count(*) from BOOKS b where b.CATEGORY_ID = c.CATEGORY_ID) as BOOK_COUNT " +
            "from CATEGORY c order by c.NAME",
            (r, n) -> {
                Category c = new Category();
                c.setCategoryId(r.getInt("CATEGORY_ID"));
                c.setName(r.getString("NAME"));
                c.setBookCount(r.getInt("BOOK_COUNT"));
                return c;
            });
    }

    public void save(Category c) {
        j.update("insert into CATEGORY(CATEGORY_ID,NAME) values(CATEGORY_SEQ.nextval,?)", c.getName().trim());
    }

    public void delete(int id) {
        j.update("delete from CATEGORY where CATEGORY_ID=?", id);
    }
}