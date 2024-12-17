package com.example.feedh.Controller;

import com.example.feedh.ApiResponse.ApiResponse;
import com.example.feedh.Model.OrderFD;
import com.example.feedh.Service.OrderFDService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderFDController {
    private final OrderFDService orderFDService;

    @GetMapping("/get")
    public ResponseEntity getAllOrders() {
        return ResponseEntity.status(200).body(orderFDService.getAllOrders());
    }

    @PostMapping("/add/{customerId}")
    public ResponseEntity addOrder(@PathVariable Integer customerId, @RequestBody @Valid OrderFD orderFD) {
        orderFDService.addOrder(customerId, orderFD);
        return ResponseEntity.status(200).body(new ApiResponse("Order has been added to customer with ID: " + customerId + " successfully"));
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity updateOrder(@PathVariable Integer orderId, @RequestBody @Valid OrderFD orderFD) {
        orderFDService.updateOrder(orderId, orderFD);
        return ResponseEntity.status(200).body(new ApiResponse("Order with ID: " + orderId + " has been updated successfully"));
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity deleteOrder(@PathVariable Integer orderId) {
        orderFDService.deleteOrder(orderId);
        return ResponseEntity.status(200).body(new ApiResponse("Order with ID: " + orderId + " has been deleted successfully"));
    }
}
