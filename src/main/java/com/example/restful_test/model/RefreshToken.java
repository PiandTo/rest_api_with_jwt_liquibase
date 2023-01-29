package com.example.restful_test.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(schema = "restful", name = "refresh_token")
@Entity
public class RefreshToken extends BaseEntity{
    private String login;
    private String password;
    private boolean isExpired;
}
