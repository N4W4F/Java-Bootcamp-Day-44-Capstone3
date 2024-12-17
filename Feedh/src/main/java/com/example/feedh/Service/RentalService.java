package com.example.feedh.Service;

import com.example.feedh.ApiResponse.ApiException;
import com.example.feedh.DTOout.RentalDTOout;
import com.example.feedh.Model.Customer;
import com.example.feedh.Model.Rental;
import com.example.feedh.Repostiory.CustomerRepository;
import com.example.feedh.Repostiory.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final CustomerRepository customerRepository;

    public List<RentalDTOout> getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        List<RentalDTOout> rentalDTOS = new ArrayList<>();

        for (Rental r : rentals) {
            rentalDTOS.add(new RentalDTOout(r.getStartDateTime(), r.getEndDateTime(), r.getPrice(), r.getStatus()));
        }
        return rentalDTOS;
    }

    public void addRental(Integer customerId, Rental rental) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("Customer with ID: " + customerId + " was not found");
        }
        rental.setCustomer(customer);
        rentalRepository.save(rental);
    }

    public void updateRental(Integer rentalId, Rental rental) {
        Rental oldRental = rentalRepository.findRentalById(rentalId);
        if (rental == null) {
            throw new ApiException("Rental with ID: " + rentalId + " was not found");
        }
        oldRental.setStartDateTime(rental.getStartDateTime());
        oldRental.setEndDateTime(rental.getEndDateTime());
        oldRental.setPrice(rental.getPrice());
        oldRental.setStatus(rental.getStatus());
        rentalRepository.save(oldRental);
    }

    public void deleteRental(Integer rentalId) {
        Rental rental = rentalRepository.findRentalById(rentalId);
        if (rental == null) {
            throw new ApiException("Rental with ID: " + rentalId + " was not found");
        }
        rentalRepository.delete(rental);
    }
}
