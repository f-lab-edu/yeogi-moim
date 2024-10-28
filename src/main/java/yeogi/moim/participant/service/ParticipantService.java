package yeogi.moim.participant.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeogi.moim.gathering.entity.Gathering;
import yeogi.moim.gathering.service.GatheringService;
import yeogi.moim.member.entity.Member;
import yeogi.moim.member.service.MemberService;
import yeogi.moim.participant.dto.ParticipantRequest;
import yeogi.moim.participant.dto.ParticipantResponse;
import yeogi.moim.participant.entity.Participant;
import yeogi.moim.participant.repository.ParticipantRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final MemberService memberService;
    private final GatheringService gatheringService;

    public ParticipantService(ParticipantRepository participantRepository, MemberService memberService, GatheringService gatheringService) {
        this.participantRepository = participantRepository;
        this.memberService = memberService;
        this.gatheringService = gatheringService;
    }

    @Transactional
    public ParticipantResponse registerParticipant(ParticipantRequest participantRequest) {
        memberService.getMemberEntity(participantRequest.getMemberId());
        gatheringService.getGatheringEntity(participantRequest.getGatheringId());

        Participant participant = participantRequest.toEntity();

        participantRepository.save(participant);

        return ParticipantResponse.from(participant);
    }

    @Transactional(readOnly = true)
    public List<ParticipantResponse> getParticipantList() {
        return participantRepository.findAll().stream()
                .map(ParticipantResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ParticipantResponse getParticipant(Long id) {
        Participant participant = participantRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Participant not found")
        );

        return ParticipantResponse.from(participant);
    }

    @Transactional
    public void deleteParticipant(Long id) {
        Participant participant = participantRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Participant not found")
        );

        participantRepository.delete(participant);
    }
}
