
package com.tnsif.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tnsif.order.entity.Order;
import com.tnsif.order.service.OrderService;

import jakarta.persistence.NoResultException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    // Get all orders
    @GetMapping("/orderservice")
    public List<Order> list() {
        return orderService.listAll();
    }
    
    // Add a new order
    @PostMapping("/orderservice")
    public void add(@RequestBody Order order) {
        orderService.save(order);
    }
    
    // Get order by ID
    @GetMapping("/orderservice/{id}")
    public ResponseEntity<Order> get(@PathVariable Long id) {
        try {
            Order order = orderService.get(id);
            return new ResponseEntity<Order>(order, HttpStatus.OK);
        } catch (NoResultException e) {
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Delete order by ID
    @DeleteMapping("/orderservice/{id}")
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }
    
    // Update an existing order
    @PutMapping("/orderservice/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody Order updateOrder) {
        try {
            Order existingOrder = orderService.get(id);
            
            // Update fields
            existingOrder.setOrderNumber(updateOrder.getOrderNumber());
            existingOrder.setCustomerName(updateOrder.getCustomerName());
            existingOrder.setCustomerEmail(updateOrder.getCustomerEmail());
            existingOrder.setShippingAddress(updateOrder.getShippingAddress());
            existingOrder.setProductName(updateOrder.getProductName());
            existingOrder.setQuantity(updateOrder.getQuantity());
            existingOrder.setPrice(updateOrder.getPrice());
            existingOrder.setTotalAmount(updateOrder.getPrice() * updateOrder.getQuantity());
            existingOrder.setPaymentMethod(updateOrder.getPaymentMethod());
            existingOrder.setOrderStatus(updateOrder.getOrderStatus());
            existingOrder.setDeliveryDate(updateOrder.getDeliveryDate());
            
            orderService.update(existingOrder);
            return new ResponseEntity<Order>(existingOrder, HttpStatus.OK);
        } catch (NoResultException e) {
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
    }
}