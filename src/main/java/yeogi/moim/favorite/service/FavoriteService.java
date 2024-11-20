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
    public FavoriteResponse addFavorite(FavoriteRequest favoriteRequest) {
        Long userId = favoriteRequest.getUserId();
        Long gatheringId = favoriteRequest.getGatheringId();

        authenticationService.authorizeMember(userId);
        gatheringService.getGathering(gatheringId);

        Favorite favoriteGathering = favoriteRepository.findByUserIdAndGatheringId(userId, gatheringId);

        if (favoriteGathering != null) {
            throw new IllegalArgumentException("이미 좋아요 누른 모임입니다.");
        }

        Favorite favorite = favoriteRequest.toEntity();

        favoriteRepository.save(favorite);

        return FavoriteResponse.from(favorite);
    }

    @Transactional(readOnly = true)
    public List<FavoriteResponse> getFavoriteList() {
        Long userId = authenticationService.getAuthenticatedMemberId();

        memberService.getMember(userId);

        return favoriteRepository.findByUserId(userId).stream()
                .map(FavoriteResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteFavorite(Long id) {
        Long userId = authenticationService.getAuthenticatedMemberId();

        memberService.getMember(userId);

        favoriteRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("삭제할 좋아요가 없습니다.")
        );

        favoriteRepository.deleteById(id);
    }
}
