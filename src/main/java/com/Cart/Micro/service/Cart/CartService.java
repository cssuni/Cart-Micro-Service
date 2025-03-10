package com.Cart.Micro.service.Cart;


import com.Cart.Micro.Repository.CartItemRepository;
import com.Cart.Micro.Repository.CartRepository;
import com.Cart.Micro.exception.ResourceNotFoundException;
import com.Cart.Micro.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    @Override
    public void clearCart(Long cartId) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        cart.getCartItems().forEach(item ->{
            cart.removeCartItem(item);
            cart.updateTotalAmount();
            cartItemRepository.delete(item);

        });

    }

    @Override
    public BigDecimal getTotalAmount(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        return cart.getTotalAmount();
    }
//
//    @Override
//    public Cart initializeNewCart(User user) {
//        Cart newCart = new Cart();
//        newCart.setTotalAmount(BigDecimal.ZERO);
//        newCart.setUser(user);
//        return cartRepository.save(newCart);
//
//    }

    @Override
    public Cart getCartByUserId(Long userId) {

        return null;

    }

    @Override
    public Cart initializeNewCart() {

        Cart newCart = new Cart();
        newCart.setTotalAmount(BigDecimal.ZERO);
        return cartRepository.save(newCart);

    }
}
