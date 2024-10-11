package yeogi.moim.gathering.dto;

import lombok.Getter;
import yeogi.moim.gathering.entity.Gathering;
import yeogi.moim.member.entity.Member;

@Getter
public class GatheringRequest {
    private String title;
    private String description;
    private int totalPersonnel;
    private Long memberId;

    public Gathering toEntity(Member member) {
        return new Gathering(
                title,
                description,
                totalPersonnel,
                1,
                member
        );
    }
}
