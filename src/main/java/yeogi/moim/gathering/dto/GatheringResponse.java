package yeogi.moim.gathering.dto;

import lombok.Builder;
import lombok.Getter;
import yeogi.moim.gathering.entity.Gathering;

@Getter
public class GatheringResponse {
    private Long id;
    private String title;
    private String description;
    private int totalPersonnel;
    private int currentPersonnel;

    @Builder
    public GatheringResponse(Long id, String title, String description, int totalPersonnel, int currentPersonnel) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.totalPersonnel = totalPersonnel;
        this.currentPersonnel = currentPersonnel;
    }

    public static GatheringResponse from(Gathering gathering) {
        return GatheringResponse.builder()
                .id(gathering.getId())
                .title(gathering.getTitle())
                .description(gathering.getDescription())
                .totalPersonnel(gathering.getTotalPersonnel())
                .currentPersonnel(gathering.getCurrentPersonnel())
                .build();
    }
}
