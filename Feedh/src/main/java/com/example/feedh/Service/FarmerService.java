package com.example.feedh.Service;

import com.example.feedh.ApiResponse.ApiException;
import com.example.feedh.DTOout.FarmerDTOout;
import com.example.feedh.Model.Customer;
import com.example.feedh.Model.Farm;
import com.example.feedh.Model.Farmer;
import com.example.feedh.Repostiory.CustomerRepository;
import com.example.feedh.Repostiory.FarmRepository;
import com.example.feedh.Repostiory.FarmerRepository;
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
        farmer.setCustomer(customer);
        farmer.setFarm(farm);
        farmerRepository.save(farmer);
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

    public void deleteFarmer(Integer farmerId) {
        Farmer farmer = farmerRepository.findFarmerById(farmerId);
        if (farmer == null) {
            throw new ApiException("Farmer with ID: " + farmerId + " was not found");
        }
        farmerRepository.delete(farmer);
    }
}
