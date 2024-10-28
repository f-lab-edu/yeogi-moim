package yeogi.moim.gathering.dto;

import lombok.Getter;
import yeogi.moim.gathering.entity.Category;

@Getter
public class SearchGatheringRequest {
    private Category category;
    private Boolean available;
    private boolean orderBy;
}