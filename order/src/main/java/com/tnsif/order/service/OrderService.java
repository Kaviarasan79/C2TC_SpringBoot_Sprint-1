
package com.tnsif.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tnsif.order.entity.Order;
import com.tnsif.order.repository.OrderRepository;


import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository repo;

   

    // List all orders
    public List<Order> listAll() {
        return repo.findAll();
    }

    // Save a new order
    public void save(Order order) {
        order.setTotalAmount(order.getPrice() * order.getQuantity());
        repo.save(order);
    }

    // Get order by ID
    public Order get(Long id) {
        return repo.findById(id).orElseThrow(NoResultException::new);
    }

    // Delete order by ID
    public void delete(Long id) {
        repo.deleteById(id);
    }

    // Update an existing order
    public void update(Order order) {
        order.setTotalAmount(order.getPrice() * order.getQuantity());
        repo.save(order);
    }
}