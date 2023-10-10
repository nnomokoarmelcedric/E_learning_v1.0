package knops.dev.auth;

import knops.dev.user.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegisterRequestTest {

    @Test
    void testConstructorAndGetters() {
        String firstname = "John";
        String lastname = "Doe";
        String email = "john.doe@example.com";
        String password = "password123";
        Role role = Role.USER;
        boolean valid = true;

        RegisterRequest registerRequest = new RegisterRequest(firstname, lastname, email, password, role, valid);

        assertEquals(firstname, registerRequest.getFirstname());
        assertEquals(lastname, registerRequest.getLastname());
        assertEquals(email, registerRequest.getEmail());
        assertEquals(password, registerRequest.getPassword());
        assertEquals(role, registerRequest.getRole());
        assertTrue(registerRequest.isValid());
    }

    @Test
    void testSetters() {
        RegisterRequest registerRequest = new RegisterRequest();

        String firstname = "John";
        String lastname = "Doe";
        String email = "john.doe@example.com";
        String password = "password123";
        Role role = Role.ADMIN;
        boolean valid = false;

        registerRequest.setFirstname(firstname);
        registerRequest.setLastname(lastname);
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setRole(role);
        registerRequest.setValid(valid);

        assertEquals(firstname, registerRequest.getFirstname());
        assertEquals(lastname, registerRequest.getLastname());
        assertEquals(email, registerRequest.getEmail());
        assertEquals(password, registerRequest.getPassword());
        assertEquals(role, registerRequest.getRole());
        assertFalse(registerRequest.isValid());
    }
}
