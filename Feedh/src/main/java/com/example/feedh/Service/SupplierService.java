package com.example.feedh.Service;

import com.example.feedh.ApiResponse.ApiException;
import com.example.feedh.DTOout.HeavyEquipmentDTOout;
import com.example.feedh.DTOout.ProductDTOout;
import com.example.feedh.DTOout.SupplierDTOout;
import com.example.feedh.Model.HeavyEquipment;
import com.example.feedh.Model.Product;
import com.example.feedh.Model.Supplier;
import com.example.feedh.Repostiory.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public List<SupplierDTOout> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        List<SupplierDTOout> supplierDTOS = new ArrayList<>();

        for (Supplier s : suppliers) {
            // ProductDTO
            List<ProductDTOout> productDTOS = new ArrayList<>();
            for (Product p : s.getProducts()) {
                productDTOS.add(new ProductDTOout(p.getName(), p.getCategory(), p.getDescription(), p.getPrice()));
            }

            // HeavyEquipmentDTO
            List<HeavyEquipmentDTOout> heavyEquipmentDTOS = new ArrayList<>();
            for (HeavyEquipment he : s.getHeavyEquipments()) {
                heavyEquipmentDTOS.add(new HeavyEquipmentDTOout(he.getName(), he.getPricePerHour(), he.getInsurance(), he.getStatus()));
            }
            // Adding SupplierDTO to the list
            supplierDTOS.add(new SupplierDTOout(s.getName(), s.getEmail(), s.getPhoneNumber(), s.getAddress(), productDTOS, heavyEquipmentDTOS));
        }
        return supplierDTOS;
    }

    public void addSupplier(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    public void updateSupplier(Integer supplierId, Supplier supplier) {
        Supplier oldSupplier = supplierRepository.findSupplierById(supplierId);
        if (oldSupplier == null) {
            throw new ApiException("Supplier with ID: " + supplierId + " was not found");
        }
        oldSupplier.setName(supplier.getName());
        oldSupplier.setEmail(supplier.getEmail());
        oldSupplier.setPhoneNumber(supplier.getPhoneNumber());
        oldSupplier.setAddress(supplier.getAddress());
        oldSupplier.setPassword(supplier.getPassword());
        oldSupplier.setCompanyRegister(supplier.getCompanyRegister());
        supplierRepository.save(oldSupplier);
    }

    public void deleteSupplier(Integer supplierId) {
        Supplier supplier = supplierRepository.findSupplierById(supplierId);
        if (supplier == null) {
            throw new ApiException("Supplier with ID: " + supplierId + " was not found");
        }
        supplierRepository.delete(supplier);
    }
}
