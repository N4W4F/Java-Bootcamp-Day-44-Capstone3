package com.example.feedh.Service;

import com.example.feedh.ApiResponse.ApiException;
import com.example.feedh.DTOout.FarmerDTOout;
import com.example.feedh.Model.Customer;
import com.example.feedh.Model.Farm;
import com.example.feedh.Model.Farmer;
import com.example.feedh.Repository.CustomerRepository;
import com.example.feedh.Repository.FarmRepository;
import com.example.feedh.Repository.FarmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmerService {
    private final FarmerRepository farmerRepository;
    private final CustomerRepository customerRepository;
    private final FarmRepository farmRepository;

    public List<FarmerDTOout> getAllFarmers() {
        List<Farmer> farmers = farmerRepository.findAll();
        List<FarmerDTOout> farmerDTOS = new ArrayList<>();

        for (Farmer f : farmers) {
            farmerDTOS.add(new FarmerDTOout(f.getName(), f.getPhoneNumber(), f.getAddress(), f.getVisaType()));
        }
        return farmerDTOS;
    }

    public void addFarmer(Integer customerId, Integer farmId, Farmer farmer) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("Customer with ID: " + customerId + " was not found");
        }

        Farm farm = farmRepository.findFarmById(farmId);
        if (farm == null) {
            throw new ApiException("Farm with ID: " + farmId + " was not found");
        }
        if (isFarmerEligible(farm, farmer)) {
            farmer.setCustomer(customer);
            farmer.setFarm(farm);
            farmerRepository.save(farmer);
        }
        else {
            throw new ApiException("Invalid assignment: The farmer's role is not suitable for the selected farm type");
        }
    }
    // Helper method for the above service
    private boolean isFarmerEligible(Farm farm, Farmer farmer) {
        String farmType = farm.getType();
        String visaType = farmer.getVisaType();

        return (farmType.equals("Agricultural") && visaType.equals("Farmer")) ||
                (farmType.equals("Livestock") && visaType.equals("Shepherd")) ||
                farmType.equals("Mixed");
    }


    public void updateFarmer(Integer farmerId, Farmer farmer) {
        Farmer oldFarmer = farmerRepository.findFarmerById(farmerId);
        if (oldFarmer == null) {
            throw new ApiException("Farmer with ID: " + farmerId + " was not found");
        }
        oldFarmer.setName(farmer.getName());
        oldFarmer.setPhoneNumber(farmer.getPhoneNumber());
        oldFarmer.setAddress(farmer.getAddress());
        oldFarmer.setPassword(farmer.getPassword());
        oldFarmer.setVisaType(farmer.getVisaType());
        farmerRepository.save(oldFarmer);
    }

    public void deleteFarmer(Integer ownerId,Integer farmerId) {
        Customer customer=customerRepository.findCustomerById(ownerId);
        Farmer farmer = farmerRepository.findFarmerById(farmerId);

        if (customer==null||farmer == null) {
            throw new ApiException("Farmer can not delete");
        }
        farmerRepository.delete(farmer);
    }
}
