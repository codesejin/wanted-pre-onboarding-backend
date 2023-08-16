package domain;

import util.constants.ErrorMessages;
import util.exception.UserBadRequestException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    default User getByEmail(String email){
        return findByEmail(email).orElseThrow(()-> new UserBadRequestException(ErrorMessages.NOT_FOUND_USER));
    }
}
