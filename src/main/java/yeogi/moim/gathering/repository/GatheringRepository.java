package yeogi.moim.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yeogi.moim.gathering.entity.Category;
import yeogi.moim.gathering.entity.Gathering;

import java.util.List;

public interface GatheringRepository extends JpaRepository<Gathering, Long> {


    List<Gathering> findAllByOrderByCreatedDateDesc();

    List<Gathering> findAllByCategory(Category category);

    @Query("SELECT g FROM Gathering g WHERE g.totalPersonnel != g.currentPersonnel")
    List<Gathering> findAllAvailableGathering();

    @Query("SELECT g FROM Gathering g WHERE g.totalPersonnel != g.currentPersonnel AND g.category = :category")
    List<Gathering> findAllAvailableGatheringByCategory(Category category);
}
