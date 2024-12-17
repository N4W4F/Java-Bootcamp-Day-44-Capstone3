package com.example.feedh.Controller;

import com.example.feedh.ApiResponse.ApiResponse;
import com.example.feedh.Model.LiveStock;
import com.example.feedh.Service.LiveStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/live-stocks")
public class LiveStockController {
    private final LiveStockService liveStockService;
    // CRUD - Start
    @GetMapping("/get")
    public ResponseEntity getAllLiveStock() {
        return ResponseEntity.status(200).body(liveStockService.getAllLiveStocks());
    }

    @PostMapping("/add/{farmId}")
    public ResponseEntity addLiveStock(@PathVariable Integer farmId, @RequestBody @Valid LiveStock liveStock) {
        liveStockService.addLiveStock(farmId, liveStock);
        return ResponseEntity.status(200).body(new ApiResponse("Live Stock has been added to farm with ID: " + farmId + " successfully"));
    }

    @PutMapping("/update/{liveStockId}")
    public ResponseEntity updateLiveStock(@PathVariable Integer liveStockId, @RequestBody @Valid LiveStock liveStock) {
        liveStockService.updateLiveStock(liveStockId, liveStock);
        return ResponseEntity.status(200).body(new ApiResponse("Live Stock with ID: " + liveStockId + " has been updated successfully"));
    }

    @DeleteMapping("/delete/{liveStockId}")
    public ResponseEntity deleteLiveStock(@PathVariable Integer liveStockId) {
        liveStockService.deleteLiveStock(liveStockId);
        return ResponseEntity.status(200).body(new ApiResponse("Live Stock with ID: " + liveStockId + " has been deleted successfully"));
    }
    // CRUD - End

    // Getters
    @GetMapping("/get/by-id/{liveStockId}")
    public ResponseEntity getLiveStockById(@PathVariable Integer liveStockId) {
        return ResponseEntity.status(200).body(liveStockService.getLiveStockById(liveStockId));
    }

    @GetMapping("/get/by-type/{customerId}/{farmId}/{type}")
    public ResponseEntity getLiveStockByType(@PathVariable Integer customerId, @PathVariable Integer farmId, @PathVariable String type) {
        return ResponseEntity.status(200).body(liveStockService.getLiveStockByType(customerId, farmId, type));
    }

    @GetMapping("/get/by-breed/{customerId}/{farmId}/{breed}")
    public ResponseEntity getLiveStockByBreed(@PathVariable Integer customerId, @PathVariable Integer farmId, @PathVariable String breed) {
        return ResponseEntity.status(200).body(liveStockService.getLiveStockByBreed(customerId, farmId, breed));
    }

    @GetMapping("/get/by-quantity/more-than/{customerId}/{farmId}/{quantity}")
    public ResponseEntity getLiveStockByQuantityGreaterThan(@PathVariable Integer customerId, @PathVariable Integer farmId, @PathVariable Integer quantity) {
        return ResponseEntity.status(200).body(liveStockService.getLiveStockByQuantityGreaterThanEqual(customerId, farmId, quantity));
    }

    @GetMapping("/get/by-quantity/less-than/{customerId}/{farmId}/{quantity}")
    public ResponseEntity getLiveStockByQuantityLessThan(@PathVariable Integer customerId, @PathVariable Integer farmId, @PathVariable Integer quantity) {
        return ResponseEntity.status(200).body(liveStockService.getLiveStockByQuantityLessThanEqual(customerId, farmId, quantity));
    }

    // Services
    @GetMapping("/get/feed-suggestions/{type}")
   public ResponseEntity<Map<String, List<String>>> getFeedSuggestions(@PathVariable String type) {
        Map<String, List<String>> feedSuggestions = liveStockService.getFeedSuggestions(type);
       return ResponseEntity.status(200).body(feedSuggestions);
    }

    @GetMapping("/get-LiveStock-BY-BreedAndType/{breed}/{type}")
    public ResponseEntity getLiveStockByBreedAndType(@PathVariable String breed,@PathVariable String type ){
        return ResponseEntity.status(200).body(liveStockService.getLiveStockByBreedAndType(breed, type));
    }

}