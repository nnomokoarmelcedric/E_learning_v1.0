package knops.dev.configs;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.web.cors.reactive.CorsWebFilter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class GatewayConfigTest {

    @Test
    public void testRoutes() {
        RouteLocatorBuilder builder = mock(RouteLocatorBuilder.class);
        RouteLocatorBuilder.Builder routeBuilder = mock(RouteLocatorBuilder.Builder.class);

        when(builder.routes()).thenReturn(routeBuilder);

        GatewayConfig gatewayConfig = new GatewayConfig();


    }

    @Test
    public void testCorsWebFilter() {
        GatewayConfig gatewayConfig = new GatewayConfig();

        CorsWebFilter corsWebFilter = gatewayConfig.corsWebFilter();

        assertNotNull(corsWebFilter);

    }
}
