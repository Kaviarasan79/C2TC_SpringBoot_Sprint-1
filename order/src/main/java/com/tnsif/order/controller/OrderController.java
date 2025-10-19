
package com.tnsif.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tnsif.order.entity.Order;
import com.tnsif.order.service.OrderService;

import jakarta.persistence.NoResultException;

@CrossOrigin(origins = "http://localhost:3000") // Allow React frontend
@RestController
@RequestMapping("/orderservice")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // ✅ Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.listAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // ✅ Add a new order
    @PostMapping
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        order.setTotalAmount(order.getPrice() * order.getQuantity());
        orderService.save(order);
        return new ResponseEntity<>("Order created successfully!", HttpStatus.CREATED);
    }

    // ✅ Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        try {
            Order order = orderService.get(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ✅ Update an existing order
    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        try {
            Order existingOrder = orderService.get(id);

            // Update fields
            existingOrder.setOrderNumber(updatedOrder.getOrderNumber());
            existingOrder.setCustomerName(updatedOrder.getCustomerName());
            existingOrder.setCustomerEmail(updatedOrder.getCustomerEmail());
            existingOrder.setShippingAddress(updatedOrder.getShippingAddress());
            existingOrder.setProductName(updatedOrder.getProductName());
            existingOrder.setQuantity(updatedOrder.getQuantity());
            existingOrder.setPrice(updatedOrder.getPrice());
            existingOrder.setTotalAmount(updatedOrder.getPrice() * updatedOrder.getQuantity());
            existingOrder.setPaymentMethod(updatedOrder.getPaymentMethod());
            existingOrder.setOrderStatus(updatedOrder.getOrderStatus());
            existingOrder.setDeliveryDate(updatedOrder.getDeliveryDate());

            orderService.update(existingOrder);
            return new ResponseEntity<>("Order updated successfully!", HttpStatus.OK);
        } catch (NoResultException e) {
            return new ResponseEntity<>("Order not found!", HttpStatus.NOT_FOUND);
        }
    }

    // ✅ Delete order by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return new ResponseEntity<>("Order deleted successfully!", HttpStatus.OK);
    }
}
