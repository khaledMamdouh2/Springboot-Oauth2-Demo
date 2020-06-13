package training.khaled.boot_oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import training.khaled.boot_oauth.model.User;

/**
 * @author Khaled
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByUsername(String username);
}
