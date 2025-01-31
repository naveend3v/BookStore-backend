package com.naveend3v.bookshop.controller;

import com.naveend3v.bookshop.entity.*;
import com.naveend3v.bookshop.jwt.JwtService;
import com.naveend3v.bookshop.jwt.UserInfoService;
import com.naveend3v.bookshop.service.BooksService;
import com.naveend3v.bookshop.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private FileUploadService fileUploadService;

    public AdminServiceController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/login")
    public ResponseEntity adminLogin(@RequestBody JwtAuthRequest JwtAuthRequest) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(JwtAuthRequest.getUsername(), JwtAuthRequest.getPassword()));

        if (auth.isAuthenticated()) {
            return  AuthResponse.generateResp(jwtService.generateToken(JwtAuthRequest.getUsername()),HttpStatus.OK);
        } else {
            return ErrorResponse.generateResp("User " + JwtAuthRequest.getUsername() + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("logout")
    public ResponseEntity adminLogout() {
        return AuthResponse.generateResp("Logged out successfully!", HttpStatus.OK);
    }

    @GetMapping("/books")
    public ResponseEntity getAllBooks() {
        return SuccessResponse.generateResp(booksService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity getBooksByID(@PathVariable Integer id) {
        Optional<Book> book = booksService.findByBook(id);
        if (book.isPresent()) {
            return BookResponse.generateResp(book.get(), HttpStatus.OK);
        } else {
            return ErrorResponse.generateResp("Book with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/books")
    public ResponseEntity addBook(@RequestBody Book newBook) {
        return BookResponse.generateResp(booksService.saveBook(newBook), HttpStatus.OK);
    }

    @PostMapping("/upload/image")
    public ResponseEntity addBook(@RequestPart("file") MultipartFile file) throws IOException {
        String path = fileUploadService.uploadImageToFileSystem(file);
        return SuccessResponse.generateResp("Image uploaded successfully! - " + path, HttpStatus.OK);
    }

    @GetMapping(value = "/download/image/{imageName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> getImage(@PathVariable String imageName) throws IOException, URISyntaxException {
        try {
            Resource resource = fileUploadService.downloadImageFromFileSystem(imageName);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            return ErrorResponse.generateResp(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> updateBooks(@RequestBody Book book, @PathVariable Integer id) {
        if (book != null) {
            return BookResponse.generateResp(booksService.updateBook(book, id), HttpStatus.OK);
        } else {
            return ErrorResponse.generateResp("Book not found with ID " + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity getAllBooks(@PathVariable Integer id) {
        Optional<Book> book = booksService.findByBook(id);
        if (book.isPresent()) {
            String bookName = book.get().getBookName();
            booksService.removeBook(id);
            return SuccessResponse.generateResp("Book " + bookName + " deleted successfully!", HttpStatus.OK);
        } else {
            return ErrorResponse.generateResp("Book not found with ID " + id, HttpStatus.NOT_FOUND);
        }
    }
}
