package yeogi.moim.participant.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeogi.moim.gathering.entity.Gathering;
import yeogi.moim.gathering.repository.GatheringRepository;
import yeogi.moim.member.entity.Member;
import yeogi.moim.member.repository.MemberRepository;
import yeogi.moim.participant.dto.ParticipantRequest;
import yeogi.moim.participant.dto.ParticipantResponse;
import yeogi.moim.participant.entity.Participant;
import yeogi.moim.participant.repository.ParticipantRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final MemberRepository memberRepository;
    private final GatheringRepository gatheringRepository;

    public ParticipantService(ParticipantRepository participantRepository, MemberRepository memberRepository, GatheringRepository gatheringRepository) {
        this.participantRepository = participantRepository;
        this.memberRepository = memberRepository;
        this.gatheringRepository = gatheringRepository;
    }

    @Transactional
    public ParticipantResponse registerParticipant(ParticipantRequest participantRequest) {
        Member member = memberRepository.findById(participantRequest.getMemberId()).orElseThrow(
                () -> new IllegalArgumentException("Member not found")
        );
        Gathering gathering = gatheringRepository.findById(participantRequest.getGatheringId()).orElseThrow(
                () -> new IllegalArgumentException("Gathering not found")
        );

        Participant participant = participantRequest.toEntity(member, gathering);

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
