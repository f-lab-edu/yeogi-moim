package yeogi.moim.gathering.controller;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yeogi.moim.gathering.dto.GatheringRequest;
import yeogi.moim.gathering.dto.GatheringResponse;
import yeogi.moim.gathering.entity.Category;
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

    @GetMapping("/search")
    public List<GatheringResponse> searchGatheringList(
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) boolean recent
    ) {
        return gatheringService.searchGatheringList(category, available, recent);

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
