package knops.dev.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    private static final String USERNAME = "testUser";
    private static final String TOKEN = "validToken";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(userDetails.getUsername()).thenReturn(USERNAME);
    }

    @Test
    void testExtractUsername() {
        String token = generateToken();
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(USERNAME, extractedUsername);
    }

    @Test
    void testExtractClaim() {
        String token = generateToken();
        String claimValue = jwtService.extractClaim(token, Claims::getSubject);
        assertEquals(USERNAME, claimValue);
    }

    @Test
    void testGenerateToken() {
        String token = jwtService.generateToken(new HashMap<>(), userDetails);
        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void testIsTokenValid() {
        String token = generateToken();
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }



    private String generateToken() {
        return jwtService.generateToken(new HashMap<>(), userDetails);
    }

    private String generateExpiredToken() {
        Date expirationDate = new Date(System.currentTimeMillis() - 1000); // Expired token
        Claims claims = Jwts.claims()
                .setSubject(USERNAME)
                .setIssuedAt(new Date(System.currentTimeMillis() - 2000))
                .setExpiration(expirationDate);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(jwtService.getSignInKey())
                .compact();
    }
}
