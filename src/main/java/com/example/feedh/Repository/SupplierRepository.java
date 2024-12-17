package com.example.feedh.Repository;

import com.example.feedh.Model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    Supplier findSupplierById(Integer id);
    List<Supplier> findSupplierByAddress(String address);

}
