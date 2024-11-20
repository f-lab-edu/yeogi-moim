package yeogi.moim.favorite.dto;

import lombok.Builder;
import lombok.Getter;
import yeogi.moim.favorite.entity.Favorite;

@Getter
public class FavoriteResponse {
    private Long id;
    private Long userId;
    private Long gatheringId;

    @Builder
    public FavoriteResponse(Long id, Long userId, Long gatheringId) {
        this.id = id;
        this.userId = userId;
        this.gatheringId = gatheringId;
    }

    public static FavoriteResponse from(Favorite favorite) {
        return FavoriteResponse.builder()
                .id(favorite.getId())
                .gatheringId(favorite.getGatheringId())
                .userId(favorite.getUserId())
                .build();
    }

}
