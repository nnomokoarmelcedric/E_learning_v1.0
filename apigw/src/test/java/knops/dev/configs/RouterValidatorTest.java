package knops.dev.configs;

import org.junit.jupiter.api.Test;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RouterValidatorTest {

    @Test
    public void testIsSecured_SecuredEndpoint() {
        RouterValidator routerValidator = new RouterValidator();

        UriComponents uriComponents = UriComponentsBuilder.fromUriString("/knops/some/secure/endpoint").build();

        ServerHttpRequest request = mock(ServerHttpRequest.class);

        when(request.getURI()).thenReturn(uriComponents.toUri());

        assertTrue(routerValidator.isSecured.test(request));
    }

    @Test
    public void testIsSecured_OpenEndpoint() {
        RouterValidator routerValidator = new RouterValidator();

        UriComponents uriComponents = UriComponentsBuilder.fromUriString("/knops/auth/authenticate").build();

        ServerHttpRequest request = mock(ServerHttpRequest.class);

        when(request.getURI()).thenReturn(uriComponents.toUri());

        assertFalse(routerValidator.isSecured.test(request));
    }

    @Test
    public void testIsSecured_UnmatchedEndpoint() {
        RouterValidator routerValidator = new RouterValidator();

        UriComponents uriComponents = UriComponentsBuilder.fromUriString("/knops/some/unmatched/endpoint").build();

        ServerHttpRequest request = mock(ServerHttpRequest.class);

        when(request.getURI()).thenReturn(uriComponents.toUri());

        assertTrue(routerValidator.isSecured.test(request));
    }

}
