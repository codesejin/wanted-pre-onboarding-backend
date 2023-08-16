package wantedpreonboardingbackend.assignment.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wantedpreonboardingbackend.assignment.domain.Member;
import wantedpreonboardingbackend.assignment.domain.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
    public UserDetailsImpl loadUserById(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + id));

        return new UserDetailsImpl(member);
    }
}

