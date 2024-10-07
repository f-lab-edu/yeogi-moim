package yeogi.moim.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeogi.moim.member.dto.MemberRequest;
import yeogi.moim.member.dto.MemberResponse;
import yeogi.moim.member.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public MemberResponse registerMember(@RequestBody MemberRequest memberRequest) {
        MemberResponse memberResponse = memberService.registerMember(memberRequest);
        return memberResponse;
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMemberList() {
        List<MemberResponse> memberResponseList = memberService.getMemberList();
        return new ResponseEntity<>(memberResponseList, HttpStatus.OK);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long memberId) {
        MemberResponse memberResponse = memberService.getMember(memberId);
        return new ResponseEntity<>(memberResponse, HttpStatus.OK);
    }
}
