package com.example.feedh.Repostiory;

import com.example.feedh.Model.LiveStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveStockRepository extends JpaRepository<LiveStock, Integer> {
    LiveStock findLiveStockById(Integer id);
}
