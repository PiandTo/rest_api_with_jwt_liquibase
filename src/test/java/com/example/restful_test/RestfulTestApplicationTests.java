package com.example.restful_test;

import com.example.restful_test.model.Role;
import com.example.restful_test.model.User;
import com.example.restful_test.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class RestfulTestApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUserController() throws Exception{
        User user = User.builder()
                .firstName("mikhail")
                .lastName("malev")
                .login("snaomi")
                .role(Role.STUDENT)
                .password("12345")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        MockHttpServletResponse response = mockMvc.perform(post("/users")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", greaterThan(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("mikhail"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("malev"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("snaomi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("STUDENT"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("12345"))
                .andExpect(status().isCreated()).andReturn().getResponse();
        Integer id = JsonPath.parse(response.getContentAsString()).read("$.id");

        mockMvc.perform(get("/users/{id}", id))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(8)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", greaterThan(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("mikhail"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("malev"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("snaomi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("STUDENT"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("12345"))
                .andExpect(status().isOk());
    }

    @Test
    public void testException() throws Exception {
        mockMvc.perform(get("/users/{id}", 1))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = User.builder()
                .firstName("Petr")
                .lastName("Petrov")
                .login("snaomi")
                .role(Role.ADMINISTRATOR)
                .password("11111")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(put("/users/{id}", 42)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(42))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Petr"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Petrov"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("snaomi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("ADMINISTRATOR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("11111"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = User.builder()
                .firstName("Dmitriy")
                .lastName("Petrov")
                .login("Hemelia")
                .role(Role.TEACHER)
                .password("11111")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        MockHttpServletResponse response = (MockHttpServletResponse) mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Dmitriy"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Petrov"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("Hemelia"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("TEACHER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("11111"))
                .andExpect(status().isCreated()).andReturn().getResponse();

        Integer id = JsonPath.parse(response.getContentAsString()).read("$.id");

        mockMvc.perform(delete("/users/{id}", id)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
