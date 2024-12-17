package com.example.feedh.Controller;

import com.example.feedh.ApiResponse.ApiResponse;
import com.example.feedh.Model.HeavyEquipment;
import com.example.feedh.Service.HeavyEquipmentService;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/heavy-equipments")
public class HeavyEquipmentController {
    private final HeavyEquipmentService heavyEquipmentService;

    @GetMapping("/get")
    public ResponseEntity getAllHeavyEquipments() {
        return ResponseEntity.status(200).body(heavyEquipmentService.getAllHeavyEquipments());
    }

    @PostMapping("/add/{supplierId}")
    public ResponseEntity addHeavyEquipment(@PathVariable Integer supplierId, @RequestBody @Valid HeavyEquipment heavyEquipment) {
        heavyEquipmentService.addHeavyEquipment(supplierId, heavyEquipment);
        return ResponseEntity.status(200).body(new ApiResponse("Heavy Equipment has been added to supplier with ID: " + supplierId + " successfully"));
    }

    @PutMapping("/update/{heavyEquipmentId}")
    public ResponseEntity updateHeavyEquipment(@PathVariable Integer heavyEquipmentId, @RequestBody @Valid HeavyEquipment heavyEquipment) {
        heavyEquipmentService.updateHeavyEquipment(heavyEquipmentId, heavyEquipment);
        return ResponseEntity.status(200).body(new ApiResponse("Heavy Equipment with ID: " + heavyEquipmentId + " has been updated successfully"));
    }

    @DeleteMapping("/delete/{heavyEquipmentId}")
    public ResponseEntity deleteHeavyEquipment(@PathVariable Integer heavyEquipmentId) {
        heavyEquipmentService.deleteHeavyEquipment(heavyEquipmentId);
        return ResponseEntity.status(200).body(new ApiResponse("Heavy Equipment with ID: " + heavyEquipmentId + " has been deleted successfully"));
    }
}
