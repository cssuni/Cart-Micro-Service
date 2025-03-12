package com.Cart.Micro.Controller;


import com.Cart.Micro.exception.ResourceNotFoundException;
import com.Cart.Micro.model.Cart;
import com.Cart.Micro.response.ApiResponse;
import com.Cart.Micro.service.Cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse> createCart(){

        return ResponseEntity.ok(new ApiResponse("Cart created successfully", cartService.initializeNewCart()));
    }

    @GetMapping("get")
    public ResponseEntity<Cart> getCart(@RequestParam Long cartId){

        try{
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok().body(cart);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("delete")
    public void clearCart(@RequestParam Long cartId){

        try{
            System.out.println("Clear Cart Triggered");
            cartService.clearCart(cartId);
        }catch (ResourceNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("totalAmount/{cartId}")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId){

        System.out.println("Cart id "+cartId);

        try{
            BigDecimal totalAmount = cartService.getTotalAmount(cartId);
            return ResponseEntity.ok(new ApiResponse("Total amount found successfully", totalAmount));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }



}
