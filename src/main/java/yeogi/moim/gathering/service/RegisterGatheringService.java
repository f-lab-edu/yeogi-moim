package yeogi.moim.gathering.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeogi.moim.authentication.service.AuthenticationService;
import yeogi.moim.gathering.dto.GatheringRequest;
import yeogi.moim.gathering.dto.GatheringResponse;
import yeogi.moim.gathering.entity.Gathering;
import yeogi.moim.gathering.repository.GatheringRepository;
import yeogi.moim.member.service.MemberService;
import yeogi.moim.participant.service.ParticipantService;

@Service
public class RegisterGatheringService {

    private final MemberService memberService;
    private final ParticipantService participantService;
    private final GatheringService gatheringService;
    private final AuthenticationService authenticationService;
    private final GatheringRepository gatheringRepository;


    public RegisterGatheringService(MemberService memberService, ParticipantService participantService, GatheringService gatheringService, AuthenticationService authenticationService, GatheringRepository gatheringRepository) {
        this.memberService = memberService;
        this.participantService = participantService;
        this.gatheringService = gatheringService;
        this.authenticationService = authenticationService;
        this.gatheringRepository = gatheringRepository;
    }

    @Transactional
    public GatheringResponse registerGathering(GatheringRequest gatheringRequest) {
        Long memberId = authenticationService.getAuthenticatedMemberId();
        memberService.getMember(memberId);

        Gathering gathering = gatheringService.registerGathering(gatheringRequest, memberId);

        participantService.registerLeaderParticipant(memberId, gathering.getId());

        return GatheringResponse.from(gathering);
    }
}
