package yeogi.moim.gathering.repository;

import yeogi.moim.gathering.entity.Category;
import yeogi.moim.gathering.entity.Gathering;

import java.util.List;

public interface GatheringRepositoryCustom {
    List<Gathering> searchGatheringList(Category category, Boolean available, boolean recent);
}
