package bd.info.habib.personaldiarybackend.repository;

import bd.info.habib.personaldiarybackend.model.Role;
import bd.info.habib.personaldiarybackend.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Optional<Role> findByName(RoleName roleName);
    Optional<Role> findByName(RoleName role);
}