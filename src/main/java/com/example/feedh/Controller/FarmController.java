package com.example.feedh.Controller;

import com.example.feedh.ApiResponse.ApiResponse;
import com.example.feedh.Model.Farm;
import com.example.feedh.Service.FarmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Nawaf - Farm Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/farm")
public class FarmController {
    private final FarmService farmService;

    @GetMapping("/get")
    public ResponseEntity getAllFarms() {
        return ResponseEntity.status(200).body(farmService.getAllFarms());
    }

    @PostMapping("/add-farm/{ownerId}")
    public ResponseEntity addFarm(@PathVariable Integer ownerId, @RequestBody @Valid Farm farm) {
        farmService.addFarm(ownerId, farm);
        return ResponseEntity.status(200).body(new ApiResponse("Farm has been added successfully"));
    }

    @PutMapping("/update/{farmId}")
    public ResponseEntity updateFarm(@PathVariable Integer farmId, @RequestBody @Valid Farm farm) {
        farmService.updateFarm(farmId, farm);
        return ResponseEntity.status(200).body(new ApiResponse("Farm with ID: " + farmId + " has been updated successfully"));
    }

    @DeleteMapping("/delete/{ownerId}/{farmId}")
    public ResponseEntity deleteFarm(@PathVariable Integer ownerId,@PathVariable Integer farmId) {
        farmService.deleteFarm(ownerId, farmId);
        return ResponseEntity.status(200).body(new ApiResponse("Farm with ID: " + farmId + " has been deleted successfully"));
    }

/// reemas
@GetMapping("/FarmByLocation/{customerId}/{farmId}/{location}")
    public ResponseEntity getFarmByLocation(@PathVariable Integer customerId,@PathVariable Integer farmId ,@PathVariable String location){
        List<Farm>farms=farmService.getFarmByLocation(customerId, farmId, location);
        return ResponseEntity.status(200).body(farms);

    }

}