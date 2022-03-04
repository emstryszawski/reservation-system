package dk.bec.polonez.reservationsystem;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.bec.polonez.reservationsystem.dto.userDto.LoginRequest;
import dk.bec.polonez.reservationsystem.dto.userDto.SignupRequest;
import dk.bec.polonez.reservationsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



// run all tests in sequence rather than just one
@SpringBootTest
@AutoConfigureMockMvc
public class ReservationSystemApplicationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void should_PersistUserInDB_when_ProperUserSignupRequestIsGiven() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setUsername("MasnyBen");
        request.setPassword("2115ToGeng");
        request.setEmail("xd@xdd.com");
        request.setName("Essasito Wariato");
        request.setRole("USER");
        mockMvc.perform(post("/api/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        assertThat(userRepository.existsByUsername("MasnyBen")).isTrue();
    }

    @Test
    public void should_PersistUserInDB_when_UserAlreadyExistsInDB() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setUsername("MasnyBen");
        request.setPassword("2115ToGeng");
        request.setEmail("xd@xdd.com");
        request.setName("Essasito Wariato");
        request.setRole("USER");

        mockMvc.perform(post("/api/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_LoginExistingUser_when_ProperCredentialsAreProvided() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("MasnyBen");
        request.setPassword("2115ToGeng");

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

//        assertThat();
    }
}
