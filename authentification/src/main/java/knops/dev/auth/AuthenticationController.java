package knops.dev.auth;


import knops.dev.user.Role;
import knops.dev.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/knops/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllVideos() {
        List<User> users = service.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PatchMapping("/user/update/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable Integer id,
            @RequestParam("email") String email,
            @RequestParam ("role")Role role,
            @RequestParam ("status")boolean status

    ) {
        try {


            boolean updated = service.updateUser(id, email, role, status);

            if (updated) {
                return ResponseEntity.ok("User updated successfully!");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating User.");
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable Integer id) {
        boolean deleted = service.deleteUserById(id);

        if (deleted) {
            return ResponseEntity.ok("User deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
