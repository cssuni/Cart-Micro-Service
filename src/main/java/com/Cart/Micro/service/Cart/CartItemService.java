package com.Cart.Micro.service.Cart;


import com.Cart.Micro.Repository.CartItemRepository;
import com.Cart.Micro.Repository.CartRepository;
import com.Cart.Micro.exception.ResourceNotFoundException;
import com.Cart.Micro.feign.ProductInterface;
import com.Cart.Micro.model.Cart;
import com.Cart.Micro.model.CartItem;
import com.Cart.Micro.model.ProductDTOforCart;
import com.Cart.Micro.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
//    private final ProductService productService;
    private final CartService cartService;
    private final ProductInterface productInterface;


    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {

        Cart cart = cartService.getCart(cartId);

        CartItem cartItem = cart.getCartItems().stream() // Stream of cart items
                .filter(item -> item.getItemId().equals(productId)) // Filter by product ID
                .findFirst() // Find the first cart item with the matching product ID
                .orElse(new CartItem()); // If not found, create a new cart item

        if(cartItem.getId() == null) {
            ProductDTOforCart product = productInterface.getProductDetails(productId);

            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
            cartItem.setProductTitle(product.getName());
            cartItem.setItemId(product.getId());
            cartItem.setProductUrl(product.getUrl());
            cartItem.setCart(cart);
            cartItemRepository.save(cartItem);

        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);

        }
        cartItem.setTotalPrice();
        cart.addCartItem(cartItem);
        cart.updateTotalAmount();
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {

        Cart cart = cartService.getCart(cartId);
        CartItem cartItem = getCartItem(cartId, productId);

        cart.removeCartItem(cartItem);
        cart.updateTotalAmount();

        cartItemRepository.delete(cartItem);
        cartRepository.save(cart);

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {

        Cart cart = cartService.getCart(cartId);
        CartItem cartItem = getCartItem(cartId, productId);

        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice();
        cart.updateTotalAmount();

        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getCartItems().stream() // Stream of cart items
                .filter(item -> item.getItemId().equals(productId)) // Filter by product ID
                .findFirst() // Find the first cart item with the matching product ID
                .orElseThrow(()-> new ResourceNotFoundException("Product not found in cart")); // If not found, Throw Exception
    }
}
