package com.example.feedh.Repostiory;

import com.example.feedh.Model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Integer> {
    Plant findPlantById(Integer Id);
}
