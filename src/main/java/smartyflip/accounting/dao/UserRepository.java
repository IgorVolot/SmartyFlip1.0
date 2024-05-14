package smartyflip.accounting.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smartyflip.accounting.model.UserAccount;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Integer> {

    Optional<UserAccount> findByUsername(String username);

    Optional<UserAccount> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
