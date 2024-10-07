package yeogi.moim.member.dto;

import lombok.Getter;
import yeogi.moim.member.entity.Member;

import java.time.LocalDateTime;

@Getter
public class MemberResponse {
    private Long member_id;
    private String username;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    private MemberResponse(Member member) {
        this.member_id = member.getId();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.createdDate = member.getCreatedDate();
        this.lastModifiedDate = member.getLastModifiedDate();
    }

    public static MemberResponse from(Member member) {
        return new MemberResponse(member);
    }
}
