package com.example.feedh.Repository;

import com.example.feedh.Model.OrderFD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderFDRepository extends JpaRepository<OrderFD, Integer> {
    OrderFD findOrderFDById(Integer id);
}
