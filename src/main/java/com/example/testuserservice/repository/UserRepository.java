package com.example.testuserservice.repository;

import com.example.testuserservice.model.Department;
import com.example.testuserservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByUpdateDateLessThanAndDepartmentId(
            @Param("updateDate") Date updateDate, Department department);
}