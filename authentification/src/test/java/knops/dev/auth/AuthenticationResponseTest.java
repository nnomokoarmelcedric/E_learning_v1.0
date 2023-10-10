package knops.dev.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthenticationResponseTest {

    @Test
    void testConstructorAndGetters() {
        String token = "exampleToken";

        AuthenticationResponse authenticationResponse = new AuthenticationResponse(token);

        assertEquals(token, authenticationResponse.getToken());
    }

    @Test
    void testSetter() {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        String token = "exampleToken";

        authenticationResponse.setToken(token);

        assertEquals(token, authenticationResponse.getToken());
    }
}
