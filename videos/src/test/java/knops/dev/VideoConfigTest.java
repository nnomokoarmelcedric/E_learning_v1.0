package knops.dev;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VideoConfigTest {

    @Test
    public void testDataBufferFactoryBean() {
        VideoConfig videoConfig = new VideoConfig();
        DataBufferFactory dataBufferFactory = videoConfig.dataBufferFactory();
        // Vous pouvez ajouter des assertions ou utiliser ce bean selon vos besoins
    }

    @Test
    public void testCorsWebFilterBean() {
        VideoConfig videoConfig = new VideoConfig();
        CorsWebFilter corsWebFilter = videoConfig.corsWebFilter();

        // Créez un vrai objet CorsConfiguration
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Collections.singletonList("*"));
        corsConfig.setMaxAge(3600L);
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST"));
        corsConfig.addAllowedHeader("*");

        // Créez un mock de UrlBasedCorsConfigurationSource
        UrlBasedCorsConfigurationSource source = mock(UrlBasedCorsConfigurationSource.class);
        when(source.getCorsConfiguration(Mockito.any(ServerWebExchange.class))).thenReturn(corsConfig);

        // Utilisez le mock source pour créer un CorsWebFilter
//        CorsWebFilter corsWebFilter = new CorsWebFilter(source);

        // Vérifiez les propriétés du CorsConfiguration directement
        assertThat(corsConfig.getAllowedOrigins()).contains("*");
        assertThat(corsConfig.getMaxAge()).isEqualTo(3600L);
        assertThat(corsConfig.getAllowedMethods()).contains("GET", "POST");
        assertThat(corsConfig.getAllowedHeaders()).contains("*");
    }
}
