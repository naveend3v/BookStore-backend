package com.naveend3v.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.naveend3v.bookshop.model.Book;

public interface BooksRepository extends JpaRepository<Book, Integer> {
}
