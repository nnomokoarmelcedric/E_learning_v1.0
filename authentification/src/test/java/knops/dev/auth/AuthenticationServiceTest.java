package knops.dev.auth;

import knops.dev.config.JwtService;
import knops.dev.user.Role;
import knops.dev.user.User;
import knops.dev.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        RegisterRequest registerRequest = new RegisterRequest("John", "Doe", "john@example.com", "password", Role.USER, true);
        User savedUser = new User(1, "John", "Doe", "john@example.com", "encodedPassword", true, Role.USER);

        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtService.generateToken(anyMap(), any(User.class))).thenReturn("exampleToken");

        AuthenticationResponse response = authenticationService.register(registerRequest);

        assertEquals("exampleToken", response.getToken());
    }

    @Test
    void testAuthenticate() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("john@example.com", "password");
        User user = new User(1, "John", "Doe", "john@example.com", "encodedPassword", true, Role.USER);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(anyMap(), any(User.class))).thenReturn("exampleToken");

        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);

        assertEquals("exampleToken", response.getToken());
    }

    @Test
    void testUpdateUser() throws IOException {
        Integer userId = 1;
        String newEmail = "newemail@example.com";
        Role newRole = Role.ADMIN;
        boolean newStatus = false;

        User existingUser = new User(userId, "John", "Doe", "john@example.com", "encodedPassword", true, Role.USER);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        boolean result = authenticationService.updateUser(userId, newEmail, newRole, newStatus);

        assertTrue(result);
        assertEquals(newEmail, existingUser.getEmail());
        assertEquals(newRole, existingUser.getRole());
        assertEquals(newStatus, existingUser.isValid());
    }

    @Test
    void testUpdateUser_UserNotFound() throws IOException {
        Integer userId = 1;
        String newEmail = "newemail@example.com";
        Role newRole = Role.ADMIN;
        boolean newStatus = false;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        boolean result = authenticationService.updateUser(userId, newEmail, newRole, newStatus);

        assertFalse(result);
    }

    @Test
    void testGetAllUsers() {
        List<User> userList = Arrays.asList(
                new User(1, "John", "Doe", "john@example.com", "encodedPassword", true, Role.USER),
                new User(2, "Jane", "Smith", "jane@example.com", "encodedPassword", true, Role.USER)
        );

        when(userRepository.findAllBy()).thenReturn(userList);

        List<User> result = authenticationService.getAllUsers();

        assertEquals(userList, result);
    }

    @Test
    void testDeleteUserById() {
        Integer userId = 1;
        User existingUser = new User(userId, "John", "Doe", "john@example.com", "encodedPassword", true, Role.USER);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        boolean result = authenticationService.deleteUserById(userId);

        assertTrue(result);
        verify(userRepository).delete(existingUser);
    }

    @Test
    void testDeleteUserById_UserNotFound() {
        Integer userId = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        boolean result = authenticationService.deleteUserById(userId);

        assertFalse(result);
    }
}
