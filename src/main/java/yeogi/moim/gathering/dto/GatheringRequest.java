package yeogi.moim.gathering.dto;

import lombok.Getter;
import yeogi.moim.gathering.entity.Category;
import yeogi.moim.gathering.entity.Gathering;

@Getter
public class GatheringRequest {
    private String title;
    private String description;
    private Category category;
    private int totalPersonnel;

    public Gathering toEntity() {
        return new Gathering(
                title,
                description,
                category,
                totalPersonnel,
                1
        );
    }
}
