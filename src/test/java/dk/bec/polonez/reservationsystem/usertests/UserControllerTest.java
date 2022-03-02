package dk.bec.polonez.reservationsystem.usertests;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.bec.polonez.reservationsystem.controller.UserController;
import dk.bec.polonez.reservationsystem.dto.userDto.SignupRequest;
import dk.bec.polonez.reservationsystem.dto.userDto.SignupResponse;
import dk.bec.polonez.reservationsystem.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void when_user_is_created_should_return_created_user() throws Exception {
        //given
        SignupRequest request = new SignupRequest();
        request.setUsername("Essa");
        request.setPassword("2115ToGeng");
        request.setEmail("xdd@xd.com");
        request.setRole("USER");

        SignupResponse user = new SignupResponse();
        user.setUsername("Essa");
        user.setEmail("xdd@xd.com");
        //when
        when(userService.createUser(any(SignupRequest.class))).thenReturn(user);

        //then
        mockMvc.perform(post("/api/users/signup")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(request.getUsername()));
    }
}
