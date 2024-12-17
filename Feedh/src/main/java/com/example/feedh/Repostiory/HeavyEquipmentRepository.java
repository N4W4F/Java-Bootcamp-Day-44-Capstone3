package com.example.feedh.Repostiory;

import com.example.feedh.Model.HeavyEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeavyEquipmentRepository extends JpaRepository<HeavyEquipment, Integer> {
    HeavyEquipment findHeavyEquipmentById(Integer id);
}
