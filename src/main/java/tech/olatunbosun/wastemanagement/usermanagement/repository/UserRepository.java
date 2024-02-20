package tech.olatunbosun.wastemanagement.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.olatunbosun.wastemanagement.usermanagement.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long aLong);
    Optional<User> findByEmail(String email);


}
