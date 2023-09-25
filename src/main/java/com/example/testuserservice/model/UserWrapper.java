package com.example.testuserservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter @Setter
public class UserWrapper {
    private String login;
    private String fullName;
    private String email;
    private Date birthDay;
}
