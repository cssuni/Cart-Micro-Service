package com.Cart.Micro.service.Cart;


import com.Cart.Micro.Repository.CartItemRepository;
import com.Cart.Micro.Repository.CartRepository;
import com.Cart.Micro.exception.ResourceNotFoundException;
import com.Cart.Micro.model.Cart;
import com.Cart.Micro.model.CartItem;
import com.Cart.Micro.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final RedisService redisService;
    private final CartItemService cartItemService;

    @Override
    public Cart getCart(Long cartId) {

        Cart cart = redisService.findCartInRedis(cartId);
        if(cart == null)
            return cartRepository.findById(cartId).stream().map(redisService::storeCartInRedis)
                    .findFirst().orElseThrow(()-> new ResourceNotFoundException("Cart Not Found"));


        return cart;



    }

    @Override
    public void clearCart(Long cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));

        Set<CartItem> cartItemSet = cart.getCartItems();
        cartItemSet.forEach(item->cartItemService.removeItemFromCart(cartId,item.getItemId()));
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
