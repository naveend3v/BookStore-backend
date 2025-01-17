package com.naveend3v.bookshop.controller;

import com.naveend3v.bookshop.entity.*;
import com.naveend3v.bookshop.service.BooksService;
import com.naveend3v.bookshop.service.JwtService;
import com.naveend3v.bookshop.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminServiceController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BooksService booksService;

    public AdminServiceController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/login")
    public ResponseEntity adminLogin(@RequestBody JwtAuthRequest JwtAuthRequest) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(JwtAuthRequest.getUsername(), JwtAuthRequest.getPassword()));

        if (auth.isAuthenticated()) {
            return new ResponseEntity<>(new AuthResponse(jwtService.generateToken(JwtAuthRequest.getUsername()), HttpStatus.OK.value(), System.currentTimeMillis()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorResponse("User " + JwtAuthRequest.getUsername() + " not found", HttpStatus.NOT_FOUND.value(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("logout")
    public ResponseEntity adminLogout() {
        return new ResponseEntity(new SuccessResponse("Logged out successfully!", HttpStatus.OK.value(), System.currentTimeMillis()), HttpStatus.OK);
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return booksService.findAll();
    }

    @PostMapping("/books/{id}")
    public ResponseEntity addBook(@PathVariable Integer id) {
        return new ResponseEntity("Book Added Successfully", HttpStatus.OK);
    }

    @PutMapping("/books")
    public Book updateBooks(@RequestBody Book book) {
        return booksService.saveBook(book);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity getAllBooks(@PathVariable Integer id) {
        Optional<Book> book = booksService.findByBook(id);
        if (book.isPresent()) {
            String bookName = book.get().getBookName();
            booksService.removeBook(id);
            return new ResponseEntity(new SuccessResponse("Book " + bookName + " deleted successfully!", HttpStatus.OK.value(), System.currentTimeMillis()), HttpStatus.OK);
        } else {
            return new ResponseEntity(new ErrorResponse("Book not found with ID " + id, HttpStatus.NOT_FOUND.value(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
        }
    }
}
