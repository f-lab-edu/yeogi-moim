package yeogi.moim.favorite.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import yeogi.moim.favorite.dto.FavoriteResponse;
import yeogi.moim.favorite.entity.Favorite;

import java.util.List;

import static yeogi.moim.favorite.entity.QFavorite.favorite;
import static yeogi.moim.gathering.entity.QGathering.gathering;

@Repository
public class FavoriteRepositoryCustomImpl implements FavoriteRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public FavoriteRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Long countFavoritesByGatheringId(Long gatheringId) {
        return queryFactory
                .select(favorite.count())
                .from(favorite)
                .where(
                        favorite.gatheringId.eq(gatheringId)
                                .and(favorite.isFavorite.isTrue())
                )
                .fetchOne();
    }

    @Override
    public List<FavoriteResponse> findByUserIdWithGathering(Long userId) {
        return queryFactory.select(Projections.constructor(FavoriteResponse.class,
                        favorite.id,
                        favorite.userId,
                        favorite.gatheringId,
                        favorite.isFavorite,
                        gathering.title,
                        gathering.description
                ))
                .from(favorite)
                .leftJoin(gathering).on(favorite.gatheringId.eq(gathering.id))
                .where(favorite.userId.eq(userId))
                .fetch();
    }
}
