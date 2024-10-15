package yeogi.moim.gathering.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeogi.moim.gathering.dto.GatheringRequest;
import yeogi.moim.gathering.dto.GatheringResponse;
import yeogi.moim.gathering.entity.Category;
import yeogi.moim.gathering.entity.Gathering;
import yeogi.moim.gathering.repository.GatheringRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GatheringService {

    private final GatheringRepository gatheringRepository;

    public GatheringService(GatheringRepository gatheringRepository) {
        this.gatheringRepository = gatheringRepository;
    }

    @Transactional
    public GatheringResponse registerGathering(GatheringRequest gatheringRequest) {
        Gathering gathering = gatheringRequest.toEntity();

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

    @Transactional(readOnly = true)
    public List<GatheringResponse> getRecentGatheringList() {
        return gatheringRepository.findAllByOrderByCreatedDateDesc().stream()
                .map(GatheringResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GatheringResponse> getGatheringListByCategory(Category category) {
        return gatheringRepository.findAllByCategory(category).stream()
                .map(GatheringResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GatheringResponse> getAvailableGatheringListByCategory(Category category) {
        return gatheringRepository.findAllAvailableGatheringByCategory(category).stream()
                .map(GatheringResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GatheringResponse> getAvailableGatheringList() {
        return gatheringRepository.findAllAvailableGathering().stream()
                .map(GatheringResponse::from)
                .collect(Collectors.toList());
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
