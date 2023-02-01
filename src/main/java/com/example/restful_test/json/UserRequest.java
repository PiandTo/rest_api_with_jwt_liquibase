package com.example.restful_test.json;

import lombok.Data;

@Data
public class UserRequest {
	private String firstName;
	private String lastName;
	private String login;
	private String password;
}
