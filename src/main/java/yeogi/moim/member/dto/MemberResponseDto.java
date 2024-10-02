package yeogi.moim.member.dto;

import lombok.Getter;
import yeogi.moim.member.entity.Member;

@Getter
public class MemberResponseDto {
    private Long member_id;
    private String username;
    private String email;

    public MemberResponseDto(Member member) {
        this.member_id = member.getId();
        this.username = member.getUsername();
        this.email = member.getEmail();
    }
}
