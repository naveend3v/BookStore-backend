package com.naveend3v.bookshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naveend3v.bookshop.entity.Book;
import com.naveend3v.bookshop.repository.BooksRepository;

@Service
public class BooksServiceImpl implements BooksService {

    private BooksRepository booksRepository;

    @Autowired
    public BooksServiceImpl(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    @Override
    public Optional<Book> findByBook(Integer id) {
        return booksRepository.findById(id);
    }

    @Override
    public Book saveBook(Book book) {
        return booksRepository.save(book);
    }

    @Override
    public void removeBook(Integer id) {
        booksRepository.findById(id).ifPresent(booksRepository::delete);
    }

    @Override
    public Book updateBook(Book book, Integer id) {
        Optional<Book> bookToUpdate = booksRepository.findById(id);
        try {
            if (bookToUpdate.isPresent()) {
                bookToUpdate.get().setBookName(book.getBookName());
                bookToUpdate.get().setAuthor(book.getAuthor());
                bookToUpdate.get().setPrice(book.getPrice());
                bookToUpdate.get().setCategory(book.getCategory());
                bookToUpdate.get().setPublishedDate(book.getPublishedDate());
                bookToUpdate.get().setBookCoverImagePath(book.getBookCoverImagePath());
                return booksRepository.save(bookToUpdate.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
