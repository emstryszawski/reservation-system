package dk.bec.polonez.reservationsystem.controller;

import dk.bec.polonez.reservationsystem.dto.userDto.*;
import dk.bec.polonez.reservationsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public SignupResponse registerUser(@RequestBody SignupRequest signUpRequest) {
        return userService.createUser(signUpRequest);
    }

    @PutMapping()
    public UpdateResponse updateUser(@RequestBody UpdateRequest updateRequest) {
        return userService.updateUser(updateRequest);
    }

    @GetMapping()
    public List<ProfileResponse> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public ProfileResponse getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping("block/{id}")
    public ProfileResponse blockUser(@PathVariable Long id) {
        return userService.block(id);
    }

    @PutMapping("unblock/{id}")
    public ProfileResponse unblockUser(@PathVariable Long id) {
        return userService.unblock(id);
    }

    @DeleteMapping("delete/{id}")
    public DeleteResponse deleteUser(@PathVariable Long id) {
        return userService.delete(id);
    }
}
