package com.example.feedh.Controller;

import com.example.feedh.ApiResponse.ApiResponse;
import com.example.feedh.Model.Supplier;
import com.example.feedh.Service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping("/get")
    public ResponseEntity getAllSuppliers() {
        return ResponseEntity.status(200).body(supplierService.getAllSuppliers());
    }

    @PostMapping("/add")
    public ResponseEntity addSupplier(@RequestBody @Valid Supplier supplier) {
        supplierService.addSupplier(supplier);
        return ResponseEntity.status(200).body(new ApiResponse("Supplier has been added successfully"));
    }

    @PutMapping("/update/{supplierId}")
    public ResponseEntity updateSupplier(@PathVariable Integer supplierId, @RequestBody @Valid Supplier supplier) {
        supplierService.updateSupplier(supplierId, supplier);
        return ResponseEntity.status(200).body(new ApiResponse("Supplier with ID: " + supplierId + " has been updated successfully"));
    }

    @DeleteMapping("/delete/{supplierId}")
    public ResponseEntity deleteSupplier(@PathVariable Integer supplierId) {
        supplierService.deleteSupplier(supplierId);
        return ResponseEntity.status(200).body(new ApiResponse("Supplier with ID: " + supplierId + " has been deleted successfully"));
    }
}
