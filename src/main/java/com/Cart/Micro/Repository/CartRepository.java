package com.Cart.Micro.Repository;

import com.Cart.Micro.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
