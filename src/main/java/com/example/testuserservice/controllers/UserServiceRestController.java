package com.example.testuserservice.controllers;

import com.example.testuserservice.model.User;
import com.example.testuserservice.repository.DepartmentRepository;
import com.example.testuserservice.repository.UserRepository;
import com.example.testuserservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserServiceRestController {

    @Autowired
    UserService userService;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    UserRepository userRepository;
    @RequestMapping("/hello")
    public String sendHello() {
        return "Hello";
    }

    @PostMapping ("/users/update/{departmentId}")
    public void updateOldUser(@PathVariable String departmentId) {
        userService.updateUsers(departmentId);
    }

    @GetMapping(path = "/users/page")
    public Page<User> loadUsersPage(
            @PageableDefault()
            Pageable pageable) {
        return userRepository.findAllActiveAdminsPageByDepartment(pageable);
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/users/pageable")
    public Page<User> findAllUsersPageable(@PageableDefault() Pageable pageable) {
        return userRepository.findActiveAdmins(pageable);
    }
}