package yeogi.moim.gathering.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeogi.moim.gathering.dto.GatheringRequest;
import yeogi.moim.gathering.dto.GatheringResponse;
import yeogi.moim.gathering.dto.SearchGatheringRequest;
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
    public GatheringResponse getGathering(Long id) {
        Gathering gathering = gatheringRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Group not found")
        );

        return GatheringResponse.from(gathering);
    }

    public List<GatheringResponse> searchGatheringList(SearchGatheringRequest searchGatheringRequest) {
        return gatheringRepository.searchGatheringList(searchGatheringRequest).stream()
                .map(GatheringResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public GatheringResponse updateGathering(Long id, GatheringRequest gatheringRequest) {
        Gathering gathering = gatheringRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Group not found")
        );

        gathering.update(
                gatheringRequest.getTitle(),
                gatheringRequest.getDescription(),
                gatheringRequest.getTotalPersonnel()
        );

        return GatheringResponse.from(gathering);
    }

    @Transactional
    public void deleteGathering(Long id) {
        gatheringRepository.deleteById(id);
    }
}
