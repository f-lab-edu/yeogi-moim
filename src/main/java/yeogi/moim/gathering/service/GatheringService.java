package yeogi.moim.gathering.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeogi.moim.gathering.dto.GatheringRequest;
import yeogi.moim.gathering.dto.GatheringResponse;
import yeogi.moim.gathering.entity.Gathering;
import yeogi.moim.gathering.repository.GatheringRepository;
import yeogi.moim.member.entity.Member;
import yeogi.moim.member.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GatheringService {

    private final GatheringRepository gatheringRepository;
    private final MemberRepository memberRepository;

    public GatheringService(GatheringRepository gatheringRepository, MemberRepository memberRepository) {
        this.gatheringRepository = gatheringRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public GatheringResponse registerGathering(GatheringRequest gatheringRequest) {

        Member member = memberRepository.findById(gatheringRequest.getMemberId()).orElseThrow(
                () -> new IllegalArgumentException("Member not found")
        );

        Gathering gathering = gatheringRequest.toEntity(member);

        gatheringRepository.save(gathering);

        return GatheringResponse.from(gathering);
    }

    @Transactional(readOnly = true)
    public List<GatheringResponse> getGatheringList() {
        return gatheringRepository.findAll().stream()
                .map(GatheringResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GatheringResponse getGathering(Long groupId) {
        Gathering gathering = gatheringRepository.findById(groupId).orElseThrow(
                () -> new RuntimeException("Group not found")
        );

        return GatheringResponse.from(gathering);
    }

    @Transactional
    public GatheringResponse updateGathering(Long groupId, GatheringRequest gatheringRequest) {
        Gathering gathering = gatheringRepository.findById(groupId).orElseThrow(
                () -> new RuntimeException("Group not found")
        );

        gathering.update(
                gatheringRequest.getTitle(),
                gatheringRequest.getDescription(),
                gatheringRequest.getTotalPersonnel()
        );

        gatheringRepository.save(gathering);

        return GatheringResponse.from(gathering);
    }

    @Transactional
    public void deleteGathering(Long groupId) {
        gatheringRepository.deleteById(groupId);
    }
}
