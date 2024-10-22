package yeogi.moim.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import yeogi.moim.member.entity.Member;
import yeogi.moim.member.repository.MemberRepository;

import java.util.ArrayList;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Email에 해당하는 계정이 존재하지 않습니다.")
        );
        return new User(member.getEmail(), member.getPassword(), new ArrayList<>());
    }
}
