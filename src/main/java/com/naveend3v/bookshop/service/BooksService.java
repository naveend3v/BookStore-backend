package com.naveend3v.bookshop.service;

import java.util.List;
import java.util.Optional;

import com.naveend3v.bookshop.model.Book;

public interface BooksService {
	List<Book> findAll();
	Optional<Book> findByBook(Integer id);
	Book saveBook(Book book);
	void removeBook(Integer id);
	Book updateBook(Book book, Integer id);
}
