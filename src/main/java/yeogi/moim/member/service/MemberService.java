package yeogi.moim.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeogi.moim.member.dto.MemberRequest;
import yeogi.moim.member.dto.MemberResponse;
import yeogi.moim.member.entity.Member;
import yeogi.moim.member.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponse registerMember(MemberRequest memberRequest) {
        memberRequest.validate();

        String encodedPassword = memberRequest.getEncodedPassword(passwordEncoder);

        Member member = new Member(
                memberRequest.getEmail(),
                memberRequest.getUsername(),
                encodedPassword
        );
        memberRepository.save(member);

        return MemberResponse.from(member);
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> getMemberList() {
        return memberRepository.findAll().stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberResponse getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Member not found")
        );
        return MemberResponse.from(member);
    }
}
