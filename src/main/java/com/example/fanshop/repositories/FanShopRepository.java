package com.example.fanshop.repositories;


import com.example.fanshop.domain.entities.FanShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FanShopRepository extends JpaRepository<FanShop,String> {
}
