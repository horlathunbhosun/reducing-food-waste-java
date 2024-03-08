package tech.olatunbosun.wastemanagement.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.olatunbosun.wastemanagement.usermanagement.models.Token;

import java.util.List;
import java.util.Optional;

/**
 * @author olulodeolatunbosun
 * @created 08/03/2024/03/2024 - 16:11
 */


public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = " select t from Token t inner join User u on t.user.id = u.id where u.id = :id and (t.expired = false or t.revoked = false)")
    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);
}
