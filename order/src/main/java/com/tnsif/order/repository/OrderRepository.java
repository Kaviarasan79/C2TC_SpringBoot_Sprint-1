
package com.tnsif.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tnsif.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
