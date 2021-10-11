package com.soft.mongodb.server;

import com.soft.mongodb.dao.BookDao;
import com.soft.mongodb.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * mongodb 服务
 * @author suphowe
 */
@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    public String save(String id, int price, String name, String info, String publish) {
        Book book = new Book();
        book.setId(id);
        book.setPrice(price);
        book.setName(name);
        book.setInfo(info);
        book.setPublish(publish);
        book.setCreateTime(new Date());
        book.setUpdateTime(new Date());
        bookDao.save(book);
        return "save success";
    }

    public List<Book> findAll() {
        return bookDao.findAll();
    }

    public Book getBookById(String id) {
        return bookDao.getBookById(id);
    }

    public Book getBookByName(String name) {
        return bookDao.getBookByName(name);
    }

    public String update(String id, int price, String name, String info, String publish) {
        Book book = new Book();
        book.setId(id);
        book.setPrice(price);
        book.setName(name);
        book.setInfo(info);
        book.setPublish(publish);
        book.setUpdateTime(new Date());
        bookDao.update(book);
        return "success";
    }

    public String deleteBook(String id, int price, String name, String info, String publish) {
        Book book = new Book();
        book.setId(id);
        book.setPrice(price);
        book.setName(name);
        book.setInfo(info);
        book.setPublish(publish);
        book.setUpdateTime(new Date());
        bookDao.deleteBook(book);
        return "success";
    }

    public String deleteBookById(String id) {
        bookDao.deleteBookById(id);
        return "success";
    }

}
