package bd.info.habib.personaldiarybackend.config;

import org.springframework.data.domain.AuditorAware;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AuditorAwareImpl extends AuditorAware<String> {

    Optional<String> getCurrentAuditor();

    Optional<LocalDateTime> getCreatedOn();
}
