package bd.info.habib.personaldiarybackend.repository;

import bd.info.habib.personaldiarybackend.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {
    Optional<UserModel> findByUsername(String username);

    boolean existsByUsername(String username);

}