package dk.bec.polonez.reservationsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.bec.polonez.reservationsystem.dto.userDto.SignupRequest;
import dk.bec.polonez.reservationsystem.exception.AuthenticationException;
import dk.bec.polonez.reservationsystem.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationSystemApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test(expected = AuthenticationException.class)
    public void should_throwAuthException_When_OtherOwnerTriesToUpdateOtherOwnersOffer() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setRole("PLACE_OWNER");
        request.setEmail("xdd@xd.com0");
        request.setPassword("2115ToGeng");
        request.setUsername("Essa");

        mockMvc.perform(post("/api/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());


    }

}
