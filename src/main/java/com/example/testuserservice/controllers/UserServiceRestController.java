package com.example.testuserservice.controllers;

import com.example.testuserservice.model.Department;
import com.example.testuserservice.model.User;
import com.example.testuserservice.model.UserWrapper;
import com.example.testuserservice.repository.DepartmentRepository;
import com.example.testuserservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
public class UserServiceRestController {

    @Autowired
    UserService userService;
    @Autowired
    DepartmentRepository departmentRepository;

    @RequestMapping("/hello")
    public String sendHello() {
        return "Hello";
    }

    @PostMapping ("/users/update/{departmentId}")
    public void updateOldUser(@PathVariable String departmentId) {
        Department department = departmentRepository.getDepartmentById(Long.valueOf(departmentId));
        List<User> users = userService.getOldUserByDepartment(department);
        for (User updatableUser : users) {
            UserWrapper userData = getUserByExternalService(updatableUser.getLogin());
            updatableUser.setFullName(userData.getFullName());
            updatableUser.setEmail(userData.getEmail());
        }
        userService.updateUsers(users);
    }

    private UserWrapper getUserByExternalService(String login) {
        String url = "https://9b421c8d-9776-44c4-bbe6-d6ccea99a640.mock.pstmn.io/users"; // TODO не забыть поменять на тестовый вариант
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<Object[]> result =
                restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class);

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.convertValue(Objects.requireNonNull(result.getBody())[0], UserWrapper.class);
    }
}