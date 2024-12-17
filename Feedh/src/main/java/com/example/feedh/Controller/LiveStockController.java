package com.example.feedh.Controller;

import com.example.feedh.ApiResponse.ApiResponse;
import com.example.feedh.Model.LiveStock;
import com.example.feedh.Service.LiveStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/live-stocks")
public class LiveStockController {
    private final LiveStockService liveStockService;

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
}
