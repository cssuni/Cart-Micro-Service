package com.Cart.Micro.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.Cart.Micro.Controller.CartController.*(..)) ||" +
            "execution(* com.Cart.Micro.Controller.CartItemController.*(..)) ||"+
            "execution(* com.Cart.Micro.service.Cart.CartService.*(..)) ||"+
            "execution(* com.Cart.Micro.service.Cart.CartItemService.*(..)) ||"+
            "execution(* com.Cart.Micro.service.redis.RedisService.*(..))"
          )
    public void logBeforeMethodCall(JoinPoint jp) {
        LOGGER.info("{} Method Called", jp.getSignature().getName());
    }

    @After("execution(* com.Cart.Micro.Controller.CartController.*(..)) ||" +
            "execution(* com.Cart.Micro.Controller.CartItemController.*(..)) ||"+
            "execution(* com.Cart.Micro.service.Cart.CartService.*(..)) ||"+
            "execution(* com.Cart.Micro.service.Cart.CartItemService.*(..)) ||"+
            "execution(* com.Cart.Micro.service.redis.RedisService.*(..))"
    )
    public void logMethodExecuted(JoinPoint jp) {
        LOGGER.info("{} Method Completed", jp.getSignature().getName());
    }
}