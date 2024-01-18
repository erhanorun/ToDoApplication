package com.project.todo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.todo.Entities.ToDoUser;
import com.project.todo.Repository.UsersRepository;
import com.project.todo.Request.LoginRequest;
import com.project.todo.Request.SignInRequest;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
@RequiredArgsConstructor
public class UserControllerTest {

    private final MockMvc mockMvc;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void it_should_signIn() throws Exception {
        // Given
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername("user");
        signInRequest.setPassword("password");

        given(usersRepository.save(any(ToDoUser.class)))
                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

        // When
        ResultActions response = mockMvc.perform(post("/signIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInRequest)));

        // Then
        response.andExpect(status().isOk()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value("user"))
                .andExpect(jsonPath("$.password").value("password"));
    }

    @Test
    public void it_should_login() throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user");
        loginRequest.setPassword("password");

        // When
        ResultActions response = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        // Then
        response.andExpect(status().isOk()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value("user"))
                .andExpect(jsonPath("$.password").value("password"));
    }
}
