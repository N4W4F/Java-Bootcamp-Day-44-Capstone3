package com.example.feedh.Service;

import com.example.feedh.ApiResponse.ApiException;
import com.example.feedh.DTOout.HeavyEquipmentDTOout;
import com.example.feedh.Model.Customer;
import com.example.feedh.Model.HeavyEquipment;
import com.example.feedh.Model.Rental;
import com.example.feedh.Model.Supplier;
import com.example.feedh.Repository.CustomerRepository;
import com.example.feedh.Repository.HeavyEquipmentRepository;
import com.example.feedh.Repository.RentalRepository;
import com.example.feedh.Repository.SupplierRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HeavyEquipmentService {
    private final HeavyEquipmentRepository heavyEquipmentRepository;
    private final SupplierRepository supplierRepository;
    private final RentalRepository rentalRepository;
    private final CustomerRepository customerRepository;
    private  final EmailService emailService;

    // CRUD - Start
    public List<HeavyEquipmentDTOout> getAllHeavyEquipments() {
        List<HeavyEquipment> heavyEquipments = heavyEquipmentRepository.findAll();
        List<HeavyEquipmentDTOout> heavyEquipmentDTOS = new ArrayList<>();

        for (HeavyEquipment he : heavyEquipments) {
            heavyEquipmentDTOS.add(new HeavyEquipmentDTOout(he.getName(), he.getPricePerHour(), he.getInsurance(), he.getStatus()));
        }
        return heavyEquipmentDTOS;
    }

    public void addHeavyEquipment(Integer supplierId, HeavyEquipment heavyEquipment) {
        Supplier supplier = supplierRepository.findSupplierById(supplierId);
        if (supplier == null) {
            throw new ApiException("Supplier with ID: " + supplierId + " was not found");
        }
        heavyEquipment.setSupplier(supplier);
        heavyEquipmentRepository.save(heavyEquipment);
    }

    public void updateHeavyEquipment(Integer heavyEquipmentId, HeavyEquipment heavyEquipment) {
        HeavyEquipment oldHeavyEquipment = heavyEquipmentRepository.findHeavyEquipmentById(heavyEquipmentId);
        if (heavyEquipment == null) {
            throw new ApiException("Heavy Equipment with ID: " + heavyEquipmentId + " was not found");
        }
        oldHeavyEquipment.setName(heavyEquipment.getName());
        oldHeavyEquipment.setPricePerHour(heavyEquipment.getPricePerHour());
        oldHeavyEquipment.setInsurance(heavyEquipment.getInsurance());
        oldHeavyEquipment.setStatus(heavyEquipment.getStatus());
        heavyEquipmentRepository.save(oldHeavyEquipment);
    }

    public void deleteHeavyEquipment(Integer heavyEquipmentId) {
        HeavyEquipment heavyEquipment = heavyEquipmentRepository.findHeavyEquipmentById(heavyEquipmentId);
        if (heavyEquipment == null) {
            throw new ApiException("Heavy Equipment with ID: " + heavyEquipmentId + " was not found");
        }
        heavyEquipmentRepository.delete(heavyEquipment);
    }
    // CRUD - End

    // Getters

    // Services
    /// //1  chang Status For The HeavyEquipment
    public void changStatusForTheHeavyEquipment(Integer heavyEquipmentId,Integer rentalId){
        HeavyEquipment heavyEquipment=heavyEquipmentRepository.findHeavyEquipmentById(heavyEquipmentId);

        Rental rental=rentalRepository.findRentalById(rentalId);

        if(rental.getStatus().equalsIgnoreCase("Completed")){
            heavyEquipment.setStatus("Available");
        }
        heavyEquipmentRepository.save(heavyEquipment);
    }

    /// 3 get HeavyEquipment By Status
    public List<HeavyEquipment>getHeavyEquipmentByStatus(Integer supplier_id,String status){

        Supplier supplier=supplierRepository.findSupplierById(supplier_id);

        if(supplier==null){
            throw new ApiException("Cannot found this ID:  "+supplier_id);
        }
        List<HeavyEquipment>heavyEquipments=heavyEquipmentRepository.findHeavyEquipmentByStatus(status);

        if(heavyEquipments.isEmpty()){
            throw new ApiException("There is no heavy Equipments hava this status ");
        }
        return heavyEquipments;
    }

    ///
//    public Double checkRentalHistoryAndNotifyDiscount(Integer supplierId, Integer customerId,HeavyEquipment heavyEquipment) {
//        Supplier supplier = supplierRepository.findSupplierById(supplierId);
//        if (supplier == null) {
//            throw new ApiException("Supplier with ID: " + supplierId + " was not found");
//        }
//
//        Customer customer = customerRepository.findCustomerById(customerId);
//        if (customer == null) {
//            throw new ApiException("Customer with ID: " + customerId + " was not found");
//        }
//
//        Integer rentalCount = 0;
//
//        for (Rental r : rentalRepository.findAll()) {
//            for (HeavyEquipment he : r.getHeavyEquipments()) {
//                if (r.getCustomer().getId().equals(customerId) &&
//                        he.getSupplier().getId().equals(supplierId)) {
//                    rentalCount++;
//                }
//            }
//        }
//
//
//        if (rentalCount >= 3) {
//            sendDiscountNotification(customer, supplier);
//            double discountedAmount = heavyEquipment.getPricePerHour()* 0.5; //
//            return discountedAmount;
//        }
//        return heavyEquipment.getPricePerHour();
//    }

//    @Transactional
//    public Double checkRentalHistoryAndNotifyDiscount(Integer supplierId, Integer customerId, HeavyEquipment heavyEquipment) {
//        // Validate supplier
//        Supplier supplier = supplierRepository.findById(supplierId)
//                .orElseThrow(() -> new ApiException("Supplier with ID: " + supplierId + " was not found"));
//
//        // Validate customer
//        Customer customer = customerRepository.findById(customerId)
//                .orElseThrow(() -> new ApiException("Customer with ID: " + customerId + " was not found"));
//
//        // Count previous rentals for this customer and supplier
//        int DISCOUNT_THRESHOLD = 3; // Minimum rentals for a discount
//        double DISCOUNT_RATE = 0.5; // 50% discount
//        int rentalCount = 0;
//
//
//        // Check for discount eligibility
//
//        for (Rental r : rentalRepository.findAll()) {
//            if (r.getCustomer().getId().equals(customerId)) {
//                for (HeavyEquipment he : r.getHeavyEquipments()) {
//                    if (he.getSupplier().getId().equals(supplierId)) {
//                        rentalCount++;
//                        System.out.println("We are in block");
//                    }
//                }
//            }
//        }
//        if (rentalCount >= DISCOUNT_THRESHOLD) {
//            sendDiscountNotification(customer, supplier);
//            return heavyEquipment.getPricePerHour() * (1 - DISCOUNT_RATE); // Apply discount
//        }
//        // Return full price if no discount applies
//        return heavyEquipment.getPricePerHour();
//    }


}