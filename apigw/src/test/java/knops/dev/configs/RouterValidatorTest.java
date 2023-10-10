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
        // Créez une instance de RouterValidator
        RouterValidator routerValidator = new RouterValidator();

        // Créez un UriComponents pour simuler une URI valide
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("/knops/some/secure/endpoint").build();

        // Créez un mock de ServerHttpRequest
        ServerHttpRequest request = mock(ServerHttpRequest.class);

        // Configurez le mock pour retourner l'URI simulée
        when(request.getURI()).thenReturn(uriComponents.toUri());

        // Appelez la méthode isSecured et vérifiez qu'elle retourne vrai (l'endpoint est sécurisé)
        assertTrue(routerValidator.isSecured.test(request));
    }

    @Test
    public void testIsSecured_OpenEndpoint() {
        // Créez une instance de RouterValidator
        RouterValidator routerValidator = new RouterValidator();

        // Créez un UriComponents pour simuler une URI valide
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("/knops/auth/authenticate").build();

        // Créez un mock de ServerHttpRequest
        ServerHttpRequest request = mock(ServerHttpRequest.class);

        // Configurez le mock pour retourner l'URI simulée
        when(request.getURI()).thenReturn(uriComponents.toUri());

        // Appelez la méthode isSecured et vérifiez qu'elle retourne faux (l'endpoint est ouvert)
        assertFalse(routerValidator.isSecured.test(request));
    }

    @Test
    public void testIsSecured_UnmatchedEndpoint() {
        // Créez une instance de RouterValidator
        RouterValidator routerValidator = new RouterValidator();

        // Créez un UriComponents pour simuler une URI valide
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("/knops/some/unmatched/endpoint").build();

        // Créez un mock de ServerHttpRequest
        ServerHttpRequest request = mock(ServerHttpRequest.class);

        // Configurez le mock pour retourner l'URI simulée
        when(request.getURI()).thenReturn(uriComponents.toUri());

        // Appelez la méthode isSecured et vérifiez qu'elle retourne vrai (l'endpoint n'est ni sécurisé ni ouvert)
        assertTrue(routerValidator.isSecured.test(request));
    }

}
