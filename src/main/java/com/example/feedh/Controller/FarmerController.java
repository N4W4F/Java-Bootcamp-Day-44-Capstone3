package com.example.feedh.Controller;

import com.example.feedh.ApiResponse.ApiResponse;
import com.example.feedh.Model.Farmer;
import com.example.feedh.Service.FarmerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/farmers")
public class FarmerController {
    private final FarmerService farmerService;

    @GetMapping("/get")
    public ResponseEntity getAllFarmers() {
        return ResponseEntity.status(200).body(farmerService.getAllFarmers());
    }

    @PostMapping("/add/{customerId}/{farmId}")
    public ResponseEntity addFarmer(@PathVariable Integer customerId, @PathVariable Integer farmId, @RequestBody @Valid Farmer farmer) {
        farmerService.addFarmer(customerId, farmId, farmer);
        return ResponseEntity.status(200).body(new ApiResponse("Farmer has been added successfully"));
    }

    @PutMapping("/update/{farmerId}")
    public ResponseEntity updateFarmer(@PathVariable Integer farmerId, @RequestBody @Valid Farmer farmer) {
        farmerService.updateFarmer(farmerId, farmer);
        return ResponseEntity.status(200).body(new ApiResponse("Farmer with ID: " + farmerId + " has been updated successfully"));
    }

    @DeleteMapping("/delete/{ownerId}/{farmerId}")
    public ResponseEntity deleteFarmer(@PathVariable Integer ownerId,@PathVariable Integer farmerId) {
        farmerService.deleteFarmer(ownerId,farmerId);
        return ResponseEntity.status(200).body(new ApiResponse("Farmer with ID: " + farmerId + " has been deleted successfully"));
    }
}
