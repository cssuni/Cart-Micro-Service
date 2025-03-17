package com.Cart.Micro.service.redis;

import com.Cart.Micro.model.Cart;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;


    public void saveData(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }


    public Cart storeCartInRedis(Cart cart){

        try{
            String userDtoJson = objectMapper.writeValueAsString(cart);
            redisTemplate.opsForValue().set("cart:"+cart.getId().toString(),userDtoJson);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return cart;
    }

    public Cart findCartInRedis(Long id){
        String productDtoJson = (String)redisTemplate.opsForValue().get("cart:"+id.toString());

        if(productDtoJson != null) {
            try {
                return objectMapper.readValue(productDtoJson, Cart.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

}
