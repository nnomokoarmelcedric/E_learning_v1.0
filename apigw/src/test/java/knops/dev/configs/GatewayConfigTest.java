package knops.dev.configs;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class GatewayConfigTest {

    @Test
    public void testRoutes() {
        // Créez un mock de RouteLocatorBuilder
        RouteLocatorBuilder builder = mock(RouteLocatorBuilder.class);
        RouteLocatorBuilder.Builder routeBuilder = mock(RouteLocatorBuilder.Builder.class);

        // Configurez le mock pour retourner le routeBuilder lors de l'appel de routes()
        when(builder.routes()).thenReturn(routeBuilder);

        // Créez une instance de GatewayConfig
        GatewayConfig gatewayConfig = new GatewayConfig();

        // Appelez la méthode routes avec le builder mock
//        gatewayConfig.routes(builder);

        // Vous pouvez ajouter d'autres assertions en fonction de votre logique spécifique.
    }

    @Test
    public void testCorsWebFilter() {
        // Créez une instance de GatewayConfig
        GatewayConfig gatewayConfig = new GatewayConfig();

        // Appelez la méthode corsWebFilter
        CorsWebFilter corsWebFilter = gatewayConfig.corsWebFilter();

        // Vérifiez que le CorsWebFilter a été créé
        assertNotNull(corsWebFilter);

        // Vous pouvez ajouter d'autres assertions pour vérifier la configuration CORS ici.
    }
}
