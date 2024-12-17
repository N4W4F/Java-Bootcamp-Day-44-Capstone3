package com.example.feedh.Controller;

import com.example.feedh.ApiResponse.ApiResponse;
import com.example.feedh.Model.HeavyEquipment;
import com.example.feedh.Model.Rental;
import com.example.feedh.Service.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rentals")
public class RentalController {
    private final RentalService rentalService;

    @GetMapping("/get")
    public ResponseEntity getAllRentals() {
        return ResponseEntity.status(200).body(rentalService.getAllRentals());
    }

    @PostMapping("/add/{customerId}/{farmer_id}/{heavyEquipment_id}")
    public ResponseEntity addRental(@PathVariable Integer customerId, @PathVariable Integer farmer_id, @PathVariable Integer heavyEquipment_id, @RequestBody @Valid Rental rental) {
        rentalService.addRental(customerId,farmer_id,heavyEquipment_id, rental);
        return ResponseEntity.status(200).body(new ApiResponse("Rental has been added to customer with ID: " + customerId + " successfully"));
    }

    @PutMapping("/update/{rentalId}")
    public ResponseEntity updateRental(@PathVariable Integer rentalId, @RequestBody @Valid Rental rental) {
        rentalService.updateRental(rentalId, rental);
        return ResponseEntity.status(200).body(new ApiResponse("Rental with ID: " + rentalId + " has been updated successfully"));
    }

    @DeleteMapping("/delete/{rentalId}")
    public ResponseEntity deleteRental(@PathVariable Integer rentalId) {
        rentalService.deleteRental(rentalId);
        return ResponseEntity.status(200).body(new ApiResponse("Rental with ID: " + rentalId + " has been deleted successfully"));
    }


       @PostMapping("/calculate-rental-price/{rentalStartDate}/{rentalEndDate}")
        public ResponseEntity calculateRentalPrice(@PathVariable LocalDateTime rentalStartDate,@PathVariable LocalDateTime rentalEndDate,@RequestBody HeavyEquipment heavyEquipment){
                 Double rentalPrice = rentalService.calculateRentalPrice(rentalStartDate, rentalEndDate, heavyEquipment);
           return ResponseEntity.status(200).body(rentalPrice);
        }



        public ResponseEntity updateRentalStatus(){
            rentalService.updateRentalStatus();
        return ResponseEntity.status(200).body(new ApiResponse("Rental status method is running"));

        }
}
