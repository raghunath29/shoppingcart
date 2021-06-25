package com.demo.shopping.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.shopping.cart.entity.ShoppingCart;
@Repository
public interface ShopingCartRepository extends  JpaRepository<ShoppingCart, String>{

}
