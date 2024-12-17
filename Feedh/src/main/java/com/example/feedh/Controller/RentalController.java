package com.example.feedh.Controller;

import com.example.feedh.ApiResponse.ApiResponse;
import com.example.feedh.Model.Rental;
import com.example.feedh.Service.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rentals")
public class RentalController {
    private final RentalService rentalService;

    @GetMapping("/get")
    public ResponseEntity getAllRentals() {
        return ResponseEntity.status(200).body(rentalService.getAllRentals());
    }

    @PostMapping("/add/{customerId}")
    public ResponseEntity addRental(@PathVariable Integer customerId, @RequestBody @Valid Rental rental) {
        rentalService.addRental(customerId, rental);
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
}
