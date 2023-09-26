package com.example.testuserservice.repository;

import com.example.testuserservice.model.Department;
import com.example.testuserservice.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByUpdateDateLessThanAndDepartmentId(
            @Param("updateDate") Date updateDate, Department department);

    @Query("select u from User u where u.roleId.id = 2L and u.active = true")
    Page<User> findActiveAdmins(Pageable pageable);


    @Query("select u.departmentId from User u " +
            "where u.roleId.id = 2L and u.active = true " +
            "group by u.departmentId " +
            "having count(u.departmentId) > 10")
    Page<User> findAllActiveAdminsPageByDepartment(Pageable pageable);
}