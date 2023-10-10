package knops.dev.user;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserTest {

    @Mock
    private UserDetails userDetails;

    @Test
    public void testGetAuthorities() {
        User user = User.builder()
                .role(Role.USER)
                .build();

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertTrue(authorities.contains(new SimpleGrantedAuthority(Role.USER.name())));
    }

    @Test
    public void testUserDetailsMethods() {
        User user = User.builder()
                .email("user@example.com")
                .password("password")
                .isValid(true)
                .role(Role.USER)
                .build();

        userDetails = mock(UserDetails.class);

        when(userDetails.getUsername()).thenReturn(user.getEmail());
        when(userDetails.getPassword()).thenReturn(user.getPassword());
        when(userDetails.isAccountNonExpired()).thenReturn(true);
        when(userDetails.isAccountNonLocked()).thenReturn(true);
        when(userDetails.isCredentialsNonExpired()).thenReturn(true);
        when(userDetails.isEnabled()).thenReturn(true);

        assertEquals(user.getEmail(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }
}
