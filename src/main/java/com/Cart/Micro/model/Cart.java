package com.Cart.Micro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter

public class Cart {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalAmount = BigDecimal.ZERO; // Initialize totalAmount to 0

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems;

//    @JsonIgnore
//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    public void addCartItem(CartItem cartItem) {
        cartItem.setCart(this);
        cartItems.add(cartItem);
        updateTotalAmount();
    }
    public void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
        cartItem.setCart(null);
        updateTotalAmount();
    }

    public void updateTotalAmount() {
        totalAmount = cartItems.stream()
                .map( item ->{
                    BigDecimal itemPrice = item.getUnitPrice();

                    if(itemPrice == null) {
                        return BigDecimal.ZERO;
                    }
                    return itemPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
                        }
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", totalAmount=" + totalAmount +
                ", cartItems=" + cartItems +
                '}';
    }
}
