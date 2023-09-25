package com.example.testuserservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "tbl_user", schema = "public", catalog = "db_testUserService")
@Getter @Setter
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "login", nullable = false, length = 50)
    private String login;
    @Basic
    @Column(name = "password", nullable = false, length = 256)
    private String password;
    @Basic
    @Column(name = "full_name", nullable = false, length = 256)
    private String fullName;
    @Basic
    @Column(name = "email", length = -1)
    private String email;
    @Basic
    @Column(name = "birth_day", nullable = false)
    private Timestamp birthDay;
    @Basic
    @Column(name = "active", nullable = false)
    private boolean active;
    @Basic
    @Column(name = "update_date", nullable = false)
    private Timestamp updateDate;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private UserRole roleId;
    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department departmentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (active != user.active) return false;
        if (!Objects.equals(login, user.login)) return false;
        if (!Objects.equals(password, user.password)) return false;
        if (!Objects.equals(fullName, user.fullName)) return false;
        if (!Objects.equals(roleId, user.roleId)) return false;
        if (!Objects.equals(email, user.email)) return false;
        if (!Objects.equals(departmentId, user.departmentId)) return false;
        if (!Objects.equals(birthDay, user.birthDay)) return false;
        return Objects.equals(updateDate, user.updateDate);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        result = 31 * result + (birthDay != null ? birthDay.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }
}
