package com.example.feedh.Service;

import com.example.feedh.ApiResponse.ApiException;
import com.example.feedh.DTOout.PlantDTOout;
import com.example.feedh.Model.Customer;
import com.example.feedh.Model.Farm;
import com.example.feedh.Model.Plant;
import com.example.feedh.Repository.CustomerRepository;
import com.example.feedh.Repository.FarmRepository;
import com.example.feedh.Repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlantService {
    private final PlantRepository plantRepository;
    private final FarmRepository farmRepository;
    private final CustomerRepository customerRepository;

    // CRUD - Start
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
    public void deletePlant(Integer farmId,Integer plantId) {
        Farm farm=farmRepository.findFarmById(farmId);
        Plant plant = plantRepository.findPlantById(plantId);
        if (farm==null||plant == null) {
            throw new ApiException("Plant with ID: " + plantId + " can not delete");
        }
        plantRepository.delete(plant);
    }
    // CRUD - End
    
    // Getters

    // Services
//    public void transferPlant(Integer farm1, Integer farm2, Integer ownerId, Integer plantId, Integer quantityToTransfer) {
//        Farm farm = farmRepository.findFarmById(farm1);
//        if (farm == null) {
//            throw new ApiException("Farm not found");
//        }
//
//        Farm farmtow = farmRepository.findFarmById(farm2);
//        if (farmtow == null) {
//            throw new ApiException("Farm not found");
//        }
//
//
//        Customer customer = customerRepository.findCustomerById(ownerId);
//        if (customer == null || (!customer.getFarms().contains(farm1) || !customer.getFarms().contains(farm2))) {
//            throw new ApiException("You must be the owner of both farms");
//        }
//
//        Optional<Plant> plantInFarm1Opt = plantRepository.findByFarm_IdAndPlantId(farm1, plantId);
//        if (!plantInFarm1Opt.isPresent()) {
//            throw new ApiException("Plant not found in source farm");
//        }
//
//        Plant plantInFarm1 = plantInFarm1Opt.get();
//
//        if (plantInFarm1.getQuantity() < quantityToTransfer) {
//            throw new ApiException("Not enough quantity to transfer");
//        }
//
//        Optional<Plant> plantInFarm2Opt = plantRepository.findByFarm_IdAndPlantId(farm2, plantId);
//        Plant plantInFarm2;
//
//        if (plantInFarm2Opt.isPresent()) {
//            plantInFarm2 = plantInFarm2Opt.get();
//        } else {
//            // اذا ما كان في نباتات في المزرعة الثانيه ب انشئ لها كائن
//            plantInFarm2 = new Plant();
//            plantInFarm2.setFarm(farmtow);
//            plantInFarm2.setPlantId(plantId);
//            plantInFarm2.setQuantity(0);
//        }
//
//        plantInFarm1.setQuantity(plantInFarm1.getQuantity() - quantityToTransfer);
//        plantInFarm2.setQuantity(plantInFarm2.getQuantity() + quantityToTransfer);
//
//        plantRepository.save(plantInFarm1);
//        plantRepository.save(plantInFarm2);
//
//        System.out.println("Successfully transferred " + quantityToTransfer + " plant from farm " + farm1 + " to farm " + farm2);
//    }
}
