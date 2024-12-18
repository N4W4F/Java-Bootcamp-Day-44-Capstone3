package com.example.feedh.Repository;

import com.example.feedh.Model.LiveStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiveStockRepository extends JpaRepository<LiveStock, Integer> {
    LiveStock findLiveStockById(Integer id);

    List<LiveStock> findLiveStockByType(String type);

    List<LiveStock> findLiveStockByBreed(String breed);

    List<LiveStock> findLiveStockByFeedType(String feedType);

    List<LiveStock> findLiveStockByQuantityGreaterThanEqual(Integer quantity);

    List<LiveStock> findLiveStockByQuantityLessThanEqual(Integer quantity);

    @Query("select ls from LiveStock ls where ls.breed=?1 and ls.type=?2")
    List<LiveStock> getLiveStockByBreedAndType(String breed ,String type);
}
