package com.Cart.Micro.service.Cart;



import com.Cart.Micro.model.Cart;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long cartId);
    void clearCart(Long cartId);
    BigDecimal getTotalAmount(Long cartId);

//    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);

    Cart initializeNewCart();
}
