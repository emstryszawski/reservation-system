package dk.bec.polonez.reservationsystem;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.bec.polonez.reservationsystem.dto.userDto.SignupRequest;
import dk.bec.polonez.reservationsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        assertThat(userRepository.existsByUsername(request.getUsername())).isTrue();
    }

    @Test(expected = ResponseStatusException.class)
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
}
