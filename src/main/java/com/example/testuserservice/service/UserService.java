package com.example.testuserservice.service;

import com.example.testuserservice.model.Department;
import com.example.testuserservice.model.User;
import com.example.testuserservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository repository;
    public List<User> getOldUserByDepartment(Department department) {
        Date date = Date.from(ZonedDateTime.now().minusMonths(1).toInstant());
        return repository.findByUpdateDateLessThanAndDepartmentId(date, department);
    }

    public void updateUsers(List<User> users) {
        repository.saveAllAndFlush(users);
    }
}
