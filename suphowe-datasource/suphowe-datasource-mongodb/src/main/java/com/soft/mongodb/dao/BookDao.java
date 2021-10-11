package com.soft.mongodb.dao;

import com.soft.mongodb.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 数据库操作
 * @author suphowe
 */
@Component
public class BookDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存对象
     */
    public void save(Book book) {
        mongoTemplate.save(book);
    }

    /**
     * 查询所有
     */
    public List<Book> findAll() {
        return mongoTemplate.findAll(Book.class);
    }

    /***
     * 根据id查询
     */
    public Book getBookById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Book.class);
    }

    /**
     * 根据名称查询
     */
    public Book getBookByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, Book.class);
    }

    /**
     * 更新对象
     */
    public void update(Book book) {
        Query query = new Query(Criteria.where("_id").is(book.getId()));
        Update update = new Update().set("publish", book.getPublish())
                .set("info", book.getInfo())
                .set("updateTime", new Date());
        //updateFirst 更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query, update, Book.class);
        //updateMulti 更新查询返回结果集的全部
//        mongoTemplate.updateMulti(query,update,Book.class);
        //upsert 更新对象不存在则去添加
//        mongoTemplate.upsert(query,update,Book.class);
    }

    /***
     * 删除对象
     */
    public void deleteBook(Book book) {
        mongoTemplate.remove(book);
    }

    /**
     * 根据id删除
     */
    public void deleteBookById(String id) {
        //findOne
        Book book = getBookById(id);
        //delete
        deleteBook(book);
    }
}
