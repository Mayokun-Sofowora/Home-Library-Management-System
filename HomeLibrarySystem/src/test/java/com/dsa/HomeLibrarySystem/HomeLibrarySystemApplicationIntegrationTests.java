package com.dsa.HomeLibrarySystem;

import com.dsa.HomeLibrarySystem.model.Member;
import com.dsa.HomeLibrarySystem.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class HomeLibrarySystemApplicationIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    /**
     * Test the integration of the login endpoint in the AuthController.
     * Mocks the memberService to return a valid member
     * and verifies that the response status is OK and body contains the member's email.
     */
    @Test
    @WithMockUser
    // Mock an authenticated user
    void testLoginIntegration() throws Exception {
        Member member = new Member();
        member.setEmail("test@example.com");
        member.setPassword("password");

        when(memberService.authenticate(anyString(), anyString())).thenReturn(Optional.of(member));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@example.com\", \"password\": \"password\"}"))
                .andExpect(status().isOk())  // This should now pass if the member is authenticated.
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }
}
