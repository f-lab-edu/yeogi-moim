package yeogi.moim.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yeogi.moim.member.dto.MemberRequestDto;
import yeogi.moim.member.dto.MemberResponseDto;
import yeogi.moim.member.entity.Member;
import yeogi.moim.member.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponseDto registerMember(MemberRequestDto memberRequestDto) {

        String password = passwordEncoder.encode(memberRequestDto.getPassword());

        Member member = new Member(
                memberRequestDto.getEmail(),
                memberRequestDto.getUsername(),
                password
        );
        memberRepository.save(member);

        return new MemberResponseDto(member);
    }

    public List<MemberResponseDto> getMemberList() {
        List<Member> members = memberRepository.findAll();
        List<MemberResponseDto> memberResponseDtoList = new ArrayList<>();

        for (Member member : members) {
            memberResponseDtoList.add(new MemberResponseDto(member));
        }
        return memberResponseDtoList;
    }

    public MemberResponseDto getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Member not found")
        );
        return new MemberResponseDto(member);
    }
}
