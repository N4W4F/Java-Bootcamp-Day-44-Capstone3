package com.example.feedh.Service;

import com.example.feedh.ApiResponse.ApiException;
import com.example.feedh.DTOout.RentalDTOout;
import com.example.feedh.Model.Customer;
import com.example.feedh.Model.Farmer;
import com.example.feedh.Model.HeavyEquipment;
import com.example.feedh.Model.Rental;
import com.example.feedh.Repository.CustomerRepository;
import com.example.feedh.Repository.FarmerRepository;
import com.example.feedh.Repository.HeavyEquipmentRepository;
import com.example.feedh.Repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final CustomerRepository customerRepository;
    private final FarmerRepository farmerRepository;
  private final HeavyEquipmentRepository heavyEquipmentRepository;
    // CRUD - Start
    public List<RentalDTOout> getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        List<RentalDTOout> rentalDTOS = new ArrayList<>();

        for (Rental r : rentals) {
            rentalDTOS.add(new RentalDTOout(r.getStartDateTime(), r.getEndDateTime(), r.getPrice(), r.getStatus()));
        }
        return rentalDTOS;
    }

    public void addRental(Integer customerId,Integer farmer_id,Integer heavyEquipment_id ,Rental rental) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("Customer with ID: " + customerId + " was not found");
        }

        Farmer farmer=farmerRepository.findFarmerById(farmer_id);
        if(farmer==null){
            throw new ApiException("Farmer with ID: " + farmer_id + " was not found");
        }
        if(!farmer.getVisaType().equalsIgnoreCase("Shepherd")){
            throw new ApiException("Sorry but the farmer must have Shepherd to use the Heavy Equipment");
        }
        HeavyEquipment heavy=heavyEquipmentRepository.findHeavyEquipmentById(heavyEquipment_id);
        if(heavy==null){
            throw new ApiException(" The Heavy Equipment with ID: " + heavy + " was not found");
        }
        if(heavy.getStatus().equalsIgnoreCase("Rented")){
            throw new ApiException("Sorry but the Heavy Equipment is Available");
        }

        rental.setCustomer(customer);
        rental.setPrice(calculateRentalPrice(rental.getStartDateTime(), rental.getEndDateTime(), heavy) + heavy.getInsurance());
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
    // CRUD - End

    // Getters

    // Services
    /// //2 calculateRentalPrice
    public Double calculateRentalPrice(LocalDateTime rentalStartDate, LocalDateTime rentalEndDate, HeavyEquipment heavyEquipment) {
        if (rentalEndDate.isBefore(rentalStartDate)) {
            throw new IllegalArgumentException("Rental end date must be after rental start date.");
        }

        long hours = ChronoUnit.HOURS.between(rentalStartDate, rentalEndDate);

        Double totalRentalPrice = hours * heavyEquipment.getPricePerHour();

        return totalRentalPrice;
    }

    /// /3 end
    @Scheduled(fixedRate = 600000) // Runs every hour
    public void updateRentalStatus(){
        LocalDateTime now=LocalDateTime.now();
        List<Rental>expiredRental=rentalRepository.getRentalByRentalEndDateAndStatus(now,"Active");
        for(Rental r:expiredRental){
            r.setStatus("Completed");

            rentalRepository.save(r);
        }
    }

}
