package yeogi.moim.favorite.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeogi.moim.authentication.service.AuthenticationService;
import yeogi.moim.gathering.dto.GatheringResponse;
import yeogi.moim.gathering.service.GatheringService;
import yeogi.moim.favorite.dto.FavoriteRequest;
import yeogi.moim.favorite.dto.FavoriteResponse;
import yeogi.moim.favorite.entity.Favorite;
import yeogi.moim.favorite.repository.FavoriteRepository;
import yeogi.moim.member.service.MemberService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final AuthenticationService authenticationService;
    private final GatheringService gatheringService;
    private final MemberService memberService;

    public FavoriteService(FavoriteRepository favoriteRepository, AuthenticationService authenticationService, GatheringService gatheringService, MemberService memberService) {
        this.favoriteRepository = favoriteRepository;
        this.authenticationService = authenticationService;
        this.gatheringService = gatheringService;
        this.memberService = memberService;
    }

    @Transactional
    public FavoriteResponse toggleFavorite(FavoriteRequest favoriteRequest) {
        Long userId = favoriteRequest.getUserId();
        Long gatheringId = favoriteRequest.getGatheringId();

        authenticationService.authorizeMember(userId);
        gatheringService.getGathering(gatheringId);

        Favorite favorite = favoriteRepository.findByUserIdAndGatheringId(userId, gatheringId);

        if (favorite != null) {
            favorite.toggleFavorite();
            return null;
        }

        Favorite newFavorite = favoriteRequest.toEntity();

        favoriteRepository.save(newFavorite);

        GatheringResponse gatheringResponse = gatheringService.getGathering(gatheringId);

        return FavoriteResponse.fromFavoriteGathering(newFavorite, gatheringResponse.getTitle(), gatheringResponse.getDescription());
    }

    @Transactional(readOnly = true)
    public List<FavoriteResponse> getFavoriteList() {
        Long userId = authenticationService.getAuthenticatedMemberId();

        memberService.getMember(userId);

        return favoriteRepository.findByUserId(userId).stream()
                .map(this::convertToFavoriteResponse)
                .toList();
    }

    private FavoriteResponse convertToFavoriteResponse(Favorite favorite) {
        GatheringResponse gatheringResponse = gatheringService.getGathering(favorite.getGatheringId());
        return FavoriteResponse.fromFavoriteGathering(
                favorite,
                gatheringResponse.getTitle(),
                gatheringResponse.getDescription()
        );
    }

}
