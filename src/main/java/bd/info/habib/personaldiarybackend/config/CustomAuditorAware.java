package bd.info.habib.personaldiarybackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableJpaAuditing
public class CustomAuditorAware implements AuditorAwareImpl{

    @Override
    public Optional<String> getCurrentAuditor() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
        } else {
            return Optional.of("anonymousUser");
        }
    }

    @Override
    public Optional<LocalDateTime> getCreatedOn() {
        return Optional.of(LocalDateTime.now());
    }
}
