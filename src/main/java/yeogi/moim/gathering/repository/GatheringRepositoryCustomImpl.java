package yeogi.moim.gathering.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import yeogi.moim.gathering.dto.SearchGatheringRequest;
import yeogi.moim.gathering.entity.Category;
import yeogi.moim.gathering.entity.Gathering;

import java.util.List;

import static yeogi.moim.gathering.entity.QGathering.gathering;

@Repository
public class GatheringRepositoryCustomImpl implements GatheringRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public GatheringRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Gathering> searchGatheringList(SearchGatheringRequest searchGatheringRequest) {
        return queryFactory
                .selectFrom(gathering)
                .where(categoryEq(searchGatheringRequest.getCategory()),
                        availableGathering(searchGatheringRequest.getAvailable()))
                .orderBy(recentOrder(searchGatheringRequest.isOrderBy()))
                .fetch();
    }

    private BooleanExpression categoryEq(Category category) {
        return category == null ? null : gathering.category.eq(category);
    }

    private BooleanExpression availableGathering(Boolean available) {
        return Boolean.TRUE.equals(available) ? gathering.totalPersonnel.ne(gathering.currentPersonnel) : null;
    }

    private OrderSpecifier<?> recentOrder(boolean recent) {
        return recent ? gathering.createdDate.desc() : gathering.createdDate.asc();
    }

}
