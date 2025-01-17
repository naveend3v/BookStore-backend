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
@RequestMapping("/user")
public class UserServiceController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BooksService booksService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserServiceController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/signup")
    public ResponseEntity addUser(@RequestBody UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfo.setRoles("ROLE_USER");
        UserInfo addedUser = userInfoService.saveUser(userInfo);
        if(addedUser == null){
            return new ResponseEntity("Error adding the user " + userInfo.getName(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity("User " + userInfo.getName()+ " registered successfully.",HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity authenticateAndGetToken(@RequestBody JwtAuthRequest JwtAuthRequest){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(JwtAuthRequest.getUsername(),JwtAuthRequest.getPassword()));

        if(auth.isAuthenticated()){
            return new ResponseEntity<>(new AuthResponse(jwtService.generateToken(JwtAuthRequest.getUsername()),HttpStatus.OK.value(),System.currentTimeMillis()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorResponse("User " + JwtAuthRequest.getUsername() + " not found",HttpStatus.NOT_FOUND.value(),System.currentTimeMillis()),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return booksService.findAll();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity getBooksByID(@PathVariable Integer id){
        Integer booksSize = booksService.findAll().size();

        Optional<Book> book = Optional.empty();

        if(id<=0 || id>booksSize || id == null){
            return new ResponseEntity<>(new ErrorResponse("Book with ID " + id + " not found",HttpStatus.NOT_FOUND.value(),System.currentTimeMillis()),HttpStatus.NOT_FOUND);
        } else {
            book = booksService.findByBook(id);
            return new ResponseEntity<>(book,HttpStatus.OK);
        }
    }
}
