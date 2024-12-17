package com.example.feedh.Repostiory;

import com.example.feedh.Model.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Integer> {
    Farm findFarmById(Integer id);
}
