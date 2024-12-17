package com.example.feedh.Service;

import com.example.feedh.ApiResponse.ApiException;
import com.example.feedh.DTOout.OrderFDDTOout;
import com.example.feedh.DTOout.ProductDTOout;
import com.example.feedh.Model.Customer;
import com.example.feedh.Model.OrderFD;
import com.example.feedh.Model.Product;
import com.example.feedh.Repostiory.CustomerRepository;
import com.example.feedh.Repostiory.OrderFDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderFDService {
    private final OrderFDRepository orderFDRepository;
    private final CustomerRepository customerRepository;

    public List<OrderFDDTOout> getAllOrders() {
        List<OrderFD> orderFDS = orderFDRepository.findAll();
        List<OrderFDDTOout> orderFDDTOS = new ArrayList<>();

        for (OrderFD o : orderFDS) {
            List<ProductDTOout> productDTOS = new ArrayList<>();
            for (Product p : o.getProducts()) {
                productDTOS.add(new ProductDTOout(p.getName(), p.getCategory(), p.getDescription(), p.getPrice()));
            }
            orderFDDTOS.add(new OrderFDDTOout(o.getOrderDateTime(), o.getQuantity(), o.getSubtotal(), o.getTotalAmount(), o.getStatus(), productDTOS));
        }
        return orderFDDTOS;
    }

    public void addOrder(Integer customerId, OrderFD orderFD) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("Customer with ID: " + customerId + " was not found");
        }
        orderFD.setCustomer(customer);
        orderFDRepository.save(orderFD);
    }

    public void updateOrder(Integer orderId, OrderFD orderFD) {
        OrderFD oldOrderFD = orderFDRepository.findOrderFDById(orderId);
        if (oldOrderFD == null) {
            throw new ApiException("Order with ID: " + orderId + " was not found");
        }
        oldOrderFD.setOrderDateTime(orderFD.getOrderDateTime());
        oldOrderFD.setQuantity(orderFD.getQuantity());
        oldOrderFD.setSubtotal(orderFD.getSubtotal());
        oldOrderFD.setTotalAmount(orderFD.getTotalAmount());
        oldOrderFD.setStatus(orderFD.getStatus());
        orderFDRepository.save(oldOrderFD);
    }

    public void deleteOrder(Integer orderId) {
        OrderFD orderFD = orderFDRepository.findOrderFDById(orderId);
        if (orderFD == null) {
            throw new ApiException("Order with ID: " + orderId + " was not found");
        }
        orderFDRepository.delete(orderFD);
    }
}
