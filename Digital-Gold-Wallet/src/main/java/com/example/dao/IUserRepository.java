package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.User;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);
}
