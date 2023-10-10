package knops.dev.auth;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthenticationRequestTest {

    @Test
    void testConstructorAndGetters() {
        String email = "john.doe@example.com";
        String password = "password123";

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);

        assertEquals(email, authenticationRequest.getEmail());
        assertEquals(password, authenticationRequest.getPassword());
    }

    @Test
    void testSetters() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();

        String email = "john.doe@example.com";
        String password = "password123";

        authenticationRequest.setEmail(email);
        authenticationRequest.setPassword(password);

        assertEquals(email, authenticationRequest.getEmail());
        assertEquals(password, authenticationRequest.getPassword());
    }
}
