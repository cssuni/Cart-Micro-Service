package com.Cart.Micro.service.Cart;


import com.Cart.Micro.Repository.CartItemRepository;
import com.Cart.Micro.Repository.CartRepository;
import com.Cart.Micro.exception.ResourceNotFoundException;
import com.Cart.Micro.model.Cart;
import com.Cart.Micro.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final RedisService redisService;

    @Override
    public Cart getCart(Long cartId) {

        Cart cart = redisService.findCartInRedis(cartId);
        if(cart == null)

            return cartRepository.findById(cartId).map(redisService::storeCartInRedis).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return cart;

    }

    @Override
    public void clearCart(Long cartId) {

//        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
//        cart.getCartItems().forEach(item ->{
//            cart.removeCartItem(item);
//            cart.updateTotalAmount();
//            cartItemRepository.delete(item);
//        });
        cartRepository.deleteById(cartId);
    }

    @Override
    public BigDecimal getTotalAmount(Long cartId) {
        Cart cart = getCart(cartId);
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
