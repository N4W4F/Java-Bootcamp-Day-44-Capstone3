package com.example.feedh.Service;

import com.example.feedh.ApiResponse.ApiException;
import com.example.feedh.DTOout.HeavyEquipmentDTOout;
import com.example.feedh.Model.HeavyEquipment;
import com.example.feedh.Model.Supplier;
import com.example.feedh.Repostiory.HeavyEquipmentRepository;
import com.example.feedh.Repostiory.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HeavyEquipmentService {
    private final HeavyEquipmentRepository heavyEquipmentRepository;
    private final SupplierRepository supplierRepository;

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
}
