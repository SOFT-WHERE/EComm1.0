package com.app.ecomm.service;

import com.app.ecomm.dto.CartItemRequest;
import com.app.ecomm.dto.CartItemResponse;
import com.app.ecomm.model.CartItem;
import com.app.ecomm.model.Product;
import com.app.ecomm.model.User;
import com.app.ecomm.repository.CartItemRepository;
import com.app.ecomm.repository.ProductRepository;
import com.app.ecomm.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {

        Optional<Product> productOpt=productRepository.findById(request.getProductId());
        if(productOpt.isEmpty()){
            return false;
        }
        Product product=productOpt.get();
        if(product.getStock()< request.getQuantity()){
            return false;
        }

        Optional<User> userOpt=userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty()){
            return false;
        }
        User user=userOpt.get();

        CartItem exsitingCartItem=cartItemRepository.findByUserAndProduct(user,product);
        if(exsitingCartItem!=null){
            //update quantity
            exsitingCartItem.setQuantity(exsitingCartItem.getQuantity()+ request.getQuantity());
            exsitingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(exsitingCartItem.getQuantity())));
            cartItemRepository.save(exsitingCartItem);
        }
        else{
            //create cart item
            CartItem cartItem=new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }


    public boolean deleteItemFromCart(String userId, Long productId) {
        Optional<Product> optProduct=productRepository.findById(productId);
        Optional<User> optUser=userRepository.findById(Long.valueOf(userId));
        if(optUser.isPresent() && optProduct.isPresent()){
            cartItemRepository.deleteByUserAndProduct(optUser.get(), optProduct.get());
            return true;
        }
        return false;
    }

    public List<CartItem> getCartItem(String userId) {

        Optional<User> optUser=userRepository.findById(Long.valueOf(userId));
        List<CartItem> ans=new ArrayList<>();
        if(optUser.isPresent()){
            ans=cartItemRepository.findByUser(optUser.get());
        }
        return ans;
    }

    public void clearCart(String userId) {
        userRepository.findById(Long.valueOf(userId)).ifPresent(user->
                cartItemRepository.deleteByUser(user)
        );
    }
}
