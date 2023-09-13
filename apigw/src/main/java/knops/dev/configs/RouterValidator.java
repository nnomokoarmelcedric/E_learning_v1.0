package knops.dev.configs;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;



@Service
public class RouterValidator {

    public static final List<String> openEndpoints = List.of(
            "/knops/auth/authenticate",
            "/knops/auth/register",
            "/knops/auth/send-email"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openEndpoints.stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}