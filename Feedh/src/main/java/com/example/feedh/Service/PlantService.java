package com.example.feedh.Service;

import com.example.feedh.ApiResponse.ApiException;
import com.example.feedh.DTOout.PlantDTOout;
import com.example.feedh.Model.Farm;
import com.example.feedh.Model.Plant;
import com.example.feedh.Repostiory.FarmRepository;
import com.example.feedh.Repostiory.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantService {
    private final PlantRepository plantRepository;
    private final FarmRepository farmRepository;

    //get plant
    public List<PlantDTOout> getAllPlants() {
        List<Plant> plants = plantRepository.findAll();
        List<PlantDTOout> plantDTOS = new ArrayList<>();

        for (Plant plant : plants) {
            plantDTOS.add(new PlantDTOout(plant.getType(), plant.getQuantity()));
        }
        return plantDTOS;
    }

    //Add new plant
    public void addPlant(Integer farmId, Plant plant) {
        Farm farm = farmRepository.findFarmById(farmId);
        if (farm == null) {
            throw new ApiException("Farm with ID: " + farmId + " was not found");
        }
        plant.setFarm(farm);
        plantRepository.save(plant);
    }

    //update plant
    public void updatePlant(Integer plantId, Plant plant) {
        Plant oldPlant = plantRepository.findPlantById(plantId);
        if (oldPlant == null) {
            throw new ApiException("Plant with ID: " + plantId + " was not found");
        }
        oldPlant.setType(plant.getType());
        oldPlant.setQuantity(plant.getQuantity());
//        oldPlant.setFarm(plant.getFarm());
        plantRepository.save(oldPlant);
    }

    //الحذف بيكون عند المزرعه
    public void deletePlant(Integer plantId) {
        Plant plant = plantRepository.findPlantById(plantId);
        if (plant == null) {
            throw new ApiException("Plant with ID: " + plantId + " was not found");
        }
        plantRepository.delete(plant);
    }
}
