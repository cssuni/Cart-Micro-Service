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

    @GetMapping("get/{cartId}")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId){

        try{
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Cart found successfully", cart));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("delete/{cartId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){

        try{
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Cart cleared successfully", null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
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
