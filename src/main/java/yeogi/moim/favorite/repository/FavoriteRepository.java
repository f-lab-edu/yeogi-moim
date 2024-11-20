package yeogi.moim.favorite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yeogi.moim.favorite.entity.Favorite;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(Long userId);
    Favorite findByUserIdAndGatheringId(Long userId, Long gatheringId);
}
