package com.soft.mongodb.controller;

import com.google.gson.Gson;
import com.soft.mongodb.server.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MongoDB 测试
 * @author suphowe
 */
@RestController
@Api(value = "MongoDB测试")
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/save")
    @ApiOperation(value = "新增")
    public String save(String id, int price, String name, String info, String publish) {
        return bookService.save(id, price, name, info, publish);
    }

    @GetMapping("/findAll")
    @ApiOperation(value = "查询全部")
    public String findAll() {
        return new Gson().toJson(bookService.findAll());
    }

    @GetMapping("/getBookById")
    @ApiOperation(value = "通过id查询")
    public String getBookById(String id) {
        return new Gson().toJson(bookService.getBookById(id));
    }

    @GetMapping("/getBookByName")
    @ApiOperation(value = "通过名称查询")
    public String getBookByName(String name) {
        return new Gson().toJson(bookService.getBookByName(name));
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新数据")
    public String update(String id, int price, String name, String info, String publish) {
        return new Gson().toJson(bookService.update(id, price, name, info, publish));
    }

    @PostMapping("/deleteBook")
    @ApiOperation(value = "删除数据")
    public String deleteBook(String id, int price, String name, String info, String publish) {
        return new Gson().toJson(bookService.deleteBook(id, price, name, info, publish));
    }

    @PostMapping("/deleteBookById")
    @ApiOperation(value = "通过id删除数据")
    public String deleteBookById(String id) {
        return new Gson().toJson(bookService.deleteBookById(id));
    }
}
