package com.example.feedh.Service;

import com.example.feedh.ApiResponse.ApiException;
import com.example.feedh.DTOout.HeavyEquipmentDTOout;
import com.example.feedh.Model.HeavyEquipment;
import com.example.feedh.Model.Rental;
import com.example.feedh.Model.Supplier;
import com.example.feedh.Repository.HeavyEquipmentRepository;
import com.example.feedh.Repository.RentalRepository;
import com.example.feedh.Repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HeavyEquipmentService {
    private final HeavyEquipmentRepository heavyEquipmentRepository;
    private final SupplierRepository supplierRepository;
    private final RentalRepository rentalRepository;

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

}
