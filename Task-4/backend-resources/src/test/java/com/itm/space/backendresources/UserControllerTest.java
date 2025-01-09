package com.itm.space.backendresources;


import com.itm.space.backendresources.api.request.UserRequest;
import com.itm.space.backendresources.api.response.UserResponse;
import com.itm.space.backendresources.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(roles = "MODERATOR")
@ExtendWith(MockitoExtension.class)
public class UserControllerTest extends BaseIntegrationTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void createUserTest() throws Exception {

        UserRequest userRequest = new UserRequest(
                "testuser",
                "testuser@example.com",
                "password123",
                "Test",
                "User"
        );

        mvc.perform(requestWithContent(post("/api/users"), userRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUserById() throws Exception {
        UUID userId = UUID.randomUUID();

        UserResponse mockUserResponse = new UserResponse(
                "John",
                "Doe",
                "john.doe@example.com",
                List.of("ROLE_USER", "ROLE_ADMIN"),
                List.of("Group1", "Group2")
        );

        when(userService.getUserById(userId)).thenReturn(mockUserResponse);

        mvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"))
                .andExpect(jsonPath("$.roles[1]").value("ROLE_ADMIN"))
                .andExpect(jsonPath("$.groups[0]").value("Group1"))
                .andExpect(jsonPath("$.groups[1]").value("Group2"));
    }

    @Test
    void testHello() throws Exception {
        mvc.perform(get("/api/users/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("user"));
    }

}
