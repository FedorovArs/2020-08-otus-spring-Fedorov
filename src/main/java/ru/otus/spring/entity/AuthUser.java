package ru.otus.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class AuthUser {

    @Id
    @Column(name = "login", length = 50)
    private String login;

    @Column(name = "position", length = 50, nullable = false)
    private String position;

    @Column(name = "password", length = 50, nullable = false)
    private String password;

    @Column(name = "authority", length = 50, nullable = false)
    private String authority;
}
