package yeogi.moim.participant.dto;

import lombok.Getter;
import yeogi.moim.gathering.entity.Gathering;
import yeogi.moim.member.entity.Member;
import yeogi.moim.participant.entity.Participant;
import yeogi.moim.participant.entity.Role;

@Getter
public class ParticipantRequest {

    private Long memberId;
    private Long gatheringId;
    private Role role;


    public Participant toEntity() {
        return new Participant(
                memberId,
                gatheringId,
                role,
                0.0
        );
    }
}
