package yeogi.moim.gathering.controller;


import org.springframework.web.bind.annotation.*;
import yeogi.moim.gathering.dto.GatheringRequest;
import yeogi.moim.gathering.dto.GatheringResponse;
import yeogi.moim.gathering.service.GatheringService;

import java.util.List;

@RestController
@RequestMapping("/api/gatherings")
public class GatheringController {

    private final GatheringService gatheringService;

    public GatheringController(GatheringService gatheringService) {
        this.gatheringService = gatheringService;
    }

    @PostMapping
    public GatheringResponse registerGathering(@RequestBody GatheringRequest gatheringRequest) {
        return gatheringService.registerGathering(gatheringRequest);
    }

    @GetMapping
    public List<GatheringResponse> getGatheringList() {
        return gatheringService.getGatheringList();
    }

    @GetMapping("/{id}")
    public GatheringResponse getGathering(@PathVariable Long id) {
        return gatheringService.getGathering(id);

    }

    @PutMapping("/{id}")
    public GatheringResponse updateGathering(@PathVariable Long id, @RequestBody GatheringRequest gatheringRequest) {
        return gatheringService.updateGathering(id, gatheringRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteGathering(@PathVariable Long id) {
        gatheringService.deleteGathering(id);
    }
}
