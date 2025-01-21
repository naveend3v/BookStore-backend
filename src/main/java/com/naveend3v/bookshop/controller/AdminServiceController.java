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

    @GetMapping("/books/{id}")
    public ResponseEntity getBooksByID(@PathVariable Integer id) {
        Optional<Book> book = booksService.findByBook(id);
        if (book.isPresent()) {
            return new ResponseEntity(book.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorResponse("Book with ID " + id + " not found", HttpStatus.NOT_FOUND.value(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/books")
    public ResponseEntity addBook(@RequestBody Book newBook) {
        Book bookToAdd = newBook;
        bookToAdd = booksService.saveBook(bookToAdd);
        Map<String, Object> resp = new HashMap<>();
        resp.put("book", bookToAdd);
        return new ResponseEntity<>(bookToAdd, HttpStatus.OK);
    }

    @PostMapping("/upload/image")
    public ResponseEntity addBook(@RequestPart("file") MultipartFile file) throws IOException {
        String path = fileUploadService.uploadImageToFileSystem(file);
        return new ResponseEntity<>(new SuccessResponse("Image uploaded successfully! - " + path, HttpStatus.OK.value(), System.currentTimeMillis()), HttpStatus.OK);
    }

    @GetMapping(value = "/download/image/{imageName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> getImage(@PathVariable String imageName) throws IOException, URISyntaxException {
        try {
            Resource resource = fileUploadService.downloadImageFromFileSystem(imageName);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/books/{id}")
    public ResponseEntity updateBooks(@PathVariable Integer id) {
        Book bookToUpdate = booksService.findByBook(id).get();
        if (bookToUpdate != null)
            return new ResponseEntity<>(booksService.saveBook(bookToUpdate), HttpStatus.OK);
        else
            return new ResponseEntity(new ErrorResponse("Book not found with ID " + id, HttpStatus.NOT_FOUND.value(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
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
