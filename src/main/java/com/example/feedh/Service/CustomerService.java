package com.example.feedh.Service;

import com.example.feedh.ApiResponse.ApiException;
import com.example.feedh.DTOout.*;
import com.example.feedh.Model.*;
import com.example.feedh.Repository.CustomerRepository;
import com.example.feedh.Repository.EventParticipantRepository;
import com.example.feedh.Repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final EventRepository eventRepository;
    private final EventParticipantRepository eventParticipantRepository;

    public List<CustomerDTOout> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTOout> customerDTOS = new ArrayList<>();

        // Customers
        for (Customer c : customers) {
            //  Farms
            List<FarmDTOout> farmDTOS = new ArrayList<>();
            for (Farm f : c.getFarms()) {
                // Farmers
                List<FarmerDTOout> farmerDTOS = new ArrayList<>();
                for (Farmer farmer : f.getFarmers()) {
                    farmerDTOS.add(new FarmerDTOout(farmer.getName(), farmer.getPhoneNumber(), farmer.getAddress(), farmer.getVisaType()));
                }

                // Live Stocks
                List<LiveStockDTOout> liveStockDTOS = new ArrayList<>();
                for (LiveStock ls : f.getLiveStocks()) {
                    liveStockDTOS.add (new LiveStockDTOout(ls.getType(), ls.getBreed(), ls.getQuantity(), ls.getFeedType()));
                }

                // Plants
                List<PlantDTOout> plantDTOS = new ArrayList<>();
                for (Plant p : f.getPlants()) {
                    plantDTOS.add(new PlantDTOout(p.getType(), p.getQuantity()));
                }
                // Finally adding FarmDTO to the list
                farmDTOS.add(new FarmDTOout(f.getName(), f.getLocation(), f.getSize(), f.getType(), farmerDTOS, plantDTOS, liveStockDTOS));
            }
            // Rentals
            List<RentalDTOout> rentalDTOS = new ArrayList<>();
            for (Rental r : c.getRentals()) {
                rentalDTOS.add(new RentalDTOout(r.getStartDateTime(), r.getEndDateTime(), r.getPrice(), r.getStatus()));
            }
            // Adding CustomerDTO to the list
            customerDTOS.add(new CustomerDTOout(c.getName(), c.getEmail(), c.getPhoneNumber(), c.getPhoneNumber(), c.getAddress(), farmDTOS, rentalDTOS));
        }
        return customerDTOS;
    }

    public void addCustomer(Customer customer) {
        if(!customer.getFoundationFile()){
            throw new ApiException(" if you don't have you Foundation File you can't register ");

        }
        customerRepository.save(customer);
    }
    public void updateCustomer(Integer customerId, Customer customer) {
        Customer oldCustomer = customerRepository.findCustomerById(customerId);
        if (oldCustomer == null) {
            throw new ApiException("Customer with ID: " + customerId + " was not found");
        }
        oldCustomer.setName(customer.getName());
        oldCustomer.setEmail(customer.getEmail());
        oldCustomer.setPhoneNumber(customer.getPhoneNumber());
        oldCustomer.setAddress(customer.getAddress());
        oldCustomer.setPassword(customer.getPassword());
        oldCustomer.setFoundationFile(customer.getFoundationFile());
        oldCustomer.setAgriculturalRegister(customer.getAgriculturalRegister());
        oldCustomer.setRegisterStatus(customer.getRegisterStatus());
        customerRepository.save(oldCustomer);
    }

    public void deleteCustomer(Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("Customer with ID: " + customerId + " was not found");
        }
        customerRepository.delete(customer);
    }
    //CURD - End

    //********** End Points *********
//    public void requestForEventParticipation(Integer customerId, Integer eventId,Integer eventPar_) {
////        EventParticipant eventParticipant=eventParticipantRepository.
//        Customer customer = customerRepository.findCustomerById(customerId);
//        if (customer == null) {
//            throw new ApiException("Customer with ID: " + customerId + " was not found");
//        }
//
//        Event event = eventRepository.findEventById(eventId);
//        if (event == null) {
//            throw new ApiException("Event with ID: " + eventId + " was not found");
//        }
//        // Optional: Send an email for admin when a user apply for a request
//        eventParticipant.getCustomers().add(customer);
//        eventParticipantRepository.save(eventParticipant);
//    }













}