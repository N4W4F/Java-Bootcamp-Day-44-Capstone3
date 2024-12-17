package com.example.feedh.Repository;

import com.example.feedh.Model.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Integer> {
    Farm findFarmById(Integer id);

    List<Farm> findFarmByLocation(String location);

}
