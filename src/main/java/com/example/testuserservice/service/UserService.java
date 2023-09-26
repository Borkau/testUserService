package com.example.testuserservice.service;

import com.example.testuserservice.model.Department;
import com.example.testuserservice.model.User;
import com.example.testuserservice.model.UserWrapper;
import com.example.testuserservice.repository.DepartmentRepository;
import com.example.testuserservice.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    UserRepository repository;
    @Autowired
    DepartmentRepository departmentRepository;

    public List<User> getOldUserByDepartment(String departmentId) {
        Department department = departmentRepository.getDepartmentById(Long.valueOf(departmentId));
        Date date = Date.from(ZonedDateTime.now().minusMonths(1).toInstant());
        return repository.findByUpdateDateLessThanAndDepartmentId(date, department);
    }

    @Transactional
    public void updateUsers(String departmentId) {
        List<User> users = getOldUserByDepartment(departmentId);
        for (User updatableUser : users) {
            UserWrapper userData = getUserByExternalService(updatableUser.getLogin());
            updatableUser.setFullName(userData.getFullName());
            updatableUser.setEmail(userData.getEmail());
        }
        repository.saveAllAndFlush(users);
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

        return objectMapper.convertValue(Objects.requireNonNull(result.getBody())[0], UserWrapper.class); // TODO возможно проще переписать на record
    }
}
