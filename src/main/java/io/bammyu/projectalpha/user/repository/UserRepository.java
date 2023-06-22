package io.bammyu.projectalpha.user.repository;

import io.bammyu.projectalpha.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByOauthIdAndOauthProvider(String oauthId, String oauthProvider);
}
