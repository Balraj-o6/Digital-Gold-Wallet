package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Address;

import java.util.List;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByCity(String city);
    List<Address> findByState(String state);
}
