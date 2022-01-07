package web.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.cart.controller.dto.request.CartSaveRequest;
import web.cart.controller.dto.response.CartItemResponse;
import web.cart.domain.Cart;
import web.cart.domain.CartItem;
import web.cart.domain.CartRepository;
import web.item.domain.Item;
import web.item.domain.ItemRepository;
import web.user.domain.User;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public List<CartItemResponse> findByUserId(User user) {
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow();

        Map<Long, CartItem> cartItem = cart.getCartItem();

        return cartItem.keySet()
                .stream()
                .map(itemId -> {
                    Item item = itemRepository.findById(itemId).orElseThrow();
                    int itemCount = cartItem.get(itemId).getItemCount();

                    return CartItemResponse.of(item, itemCount);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Cart save(CartSaveRequest cartSaveRequest, User user) {
        Optional<Cart> cart = cartRepository.findByUserId(user.getId());
        CartItem cartItem = new CartItem(cartSaveRequest.getItemId(), cartSaveRequest.getItemCount());

        if (cart.isPresent()) {
            Cart existCart = cart.get();
            existCart.addCartItem(cartItem);

            return cartRepository.save(existCart);
        }

        Map<Long, CartItem> initCart = new HashMap<>();
        initCart.put(cartItem.getItemId(), cartItem);

        Cart newCart = Cart.builder()
                .userId(user.getId())
                .cartItem(initCart)
                .build();

        return cartRepository.save(newCart);
    }

    @Transactional
    public void delete(Long itemId, User user) {
        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow();

        Map<Long, CartItem> cartItem = cart.getCartItem();
        cartItem.remove(itemId);
    }

}
