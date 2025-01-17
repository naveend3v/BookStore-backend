package com.naveend3v.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.naveend3v.bookshop.entity.Book;

public interface BooksRepository extends JpaRepository<Book, Integer> {
}
