package com.itm.space.backendresources;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.itm.space.backendresources.api.request.UserRequest;
import com.itm.space.backendresources.api.response.UserResponse;
import com.itm.space.backendresources.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import javax.ws.rs.core.Response;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @Autowired
    private UserService userService; // Тестируемый сервис

    @Autowired
    private Keycloak keycloak; // Настоящий клиент Keycloak

    @Value("${keycloak.realm}")
    private String realm;


    @BeforeAll
    void setUp() {
        assertNotNull(keycloak);
    }

    @Test
    void testCreateUser() {
        UserRequest userRequest = new UserRequest(
                "testuser3",
                "testuser3@example.com",
                "password123",
                "Test3",
                "User3"
        );

        assertDoesNotThrow(() -> userService.createUser(userRequest));

        UserRepresentation createdUser = keycloak.realm(realm)
                .users()
                .search(userRequest.getUsername())
                .stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("User not created in Keycloak"));

        assertEquals(userRequest.getEmail(), createdUser.getEmail());
        assertEquals(userRequest.getFirstName(), createdUser.getFirstName());
        assertEquals(userRequest.getLastName(), createdUser.getLastName());
    }

    @Test
    void testGetUserById() {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername("tempuser");
        newUser.setEmail("tempuser@example.com");
        newUser.setFirstName("Temp");
        newUser.setLastName("User");
        newUser.setEnabled(true);

        Response response = keycloak.realm(realm).users().create(newUser);
        String userId = CreatedResponseUtil.getCreatedId(response);

        UserResponse userResponse = userService.getUserById(UUID.fromString(userId));
        assertNotNull(userResponse);
        assertEquals(newUser.getFirstName(), userResponse.getFirstName());
        assertEquals(newUser.getLastName(), userResponse.getLastName());
    }


}



