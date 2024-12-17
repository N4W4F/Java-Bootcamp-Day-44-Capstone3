package com.example.feedh.Controller;
import com.example.feedh.ApiResponse.ApiResponse;
import com.example.feedh.Model.Plant;
import com.example.feedh.Service.PlantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/plants")
public class PlantController {
    private final PlantService plantService;

    @GetMapping("/get")
    public ResponseEntity getAllPlants() {
        return ResponseEntity.status(200).body(plantService.getAllPlants());
    }

    @PostMapping("/add/{farmId}")
    public ResponseEntity addFarm(@PathVariable Integer farmId, @RequestBody @Valid Plant plant) {
        plantService.addPlant(farmId, plant);
        return ResponseEntity.status(200).body(new ApiResponse("Plant has been added to farm with ID: " + farmId + " successfully"));

    }

    @PutMapping("/update/{plantId}")
    public ResponseEntity updatePlant(@PathVariable Integer plantId, @RequestBody @Valid Plant plant) {
        plantService.updatePlant(plantId, plant);
        return ResponseEntity.status(200).body(new ApiResponse("Plant with ID: " + plantId + " has been updated successfully"));
    }
@DeleteMapping("/delete/{farmId}/{plantId}")
    public ResponseEntity deletePlant(@PathVariable Integer farmId,@PathVariable Integer plantId){
        plantService.deletePlant(farmId, plantId);
        return ResponseEntity.status(200).body(new ApiResponse("Plant with ID: " + plantId + " has been updated successfully"));
    }

//    @PostMapping("/transfer/{farm1}/{farm2}/{ownerId}/{plantId}/{quantityToTransfer}")
//    public ResponseEntity transferPlant(
//            @PathVariable Integer farm1,
//            @PathVariable Integer farm2,
//            @PathVariable Integer ownerId,
//            @PathVariable Integer plantId,
//            @PathVariable Integer quantityToTransfer) {
//        plantService.transferPlant(farm1, farm2, ownerId, plantId, quantityToTransfer);
//        return ResponseEntity.status(200)
//                .body(new ApiResponse("Successfully transferred " + quantityToTransfer + " plants from farm " + farm1 + " to farm " + farm2));
//    }
}