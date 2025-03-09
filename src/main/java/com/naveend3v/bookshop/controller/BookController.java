package com.naveend3v.bookshop.controller;

import com.naveend3v.bookshop.dto.response.BookResponse;
import com.naveend3v.bookshop.dto.response.SuccessResponse;
import com.naveend3v.bookshop.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BookController {

    @Autowired
    private BooksService booksService;

    public BookController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping("/welcome")
    public ResponseEntity welcome() {
        return SuccessResponse.generateResp("Welcome to Book Store",HttpStatus.OK);
    }

    @GetMapping("/books")
    public ResponseEntity getAllBooks(){
        return BookResponse.generateResp(booksService.findAll(),HttpStatus.OK);
    }

}
