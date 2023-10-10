package knops.dev.config;

import knops.dev.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
@Import(ApplicationConfig.class)
class ApplicationConfigTest {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void userDetailsServiceBean() {
        ApplicationConfig applicationConfig = new ApplicationConfig(userRepository);
        UserDetailsService userDetailsService = applicationConfig.userDetailsService();
        assertNotNull(userDetailsService);
    }

    @Test
    void authenticationProviderBean() {
        ApplicationConfig applicationConfig = new ApplicationConfig(userRepository);
        AuthenticationProvider authenticationProvider = applicationConfig.authenticationProvider();
        assertNotNull(authenticationProvider);
    }

    @Test
    void authenticationManagerBean() throws Exception {
        ApplicationConfig applicationConfig = new ApplicationConfig(userRepository);
        AuthenticationConfiguration authenticationConfiguration = mock(AuthenticationConfiguration.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(mock(AuthenticationManager.class));
        AuthenticationManager authenticationManager = applicationConfig.authenticationManager(authenticationConfiguration);
        assertNotNull(authenticationManager);
    }

    @Test
    void passwordEncoderBean() {
        ApplicationConfig applicationConfig = new ApplicationConfig(userRepository);
        PasswordEncoder passwordEncoder = applicationConfig.passwordEncoder();
        assertNotNull(passwordEncoder);
    }
}
