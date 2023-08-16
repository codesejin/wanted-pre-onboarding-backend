package wantedpreonboardingbackend.assignment.domain;

import wantedpreonboardingbackend.assignment.util.constants.ErrorMessages;
import wantedpreonboardingbackend.assignment.util.exception.UserBadRequestException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    default Member getByEmail(String email){
        return findByEmail(email).orElseThrow(()-> new UserBadRequestException(ErrorMessages.NOT_FOUND_USER));
    }
}
