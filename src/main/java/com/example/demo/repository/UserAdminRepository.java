package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserAdmin;

@Repository
public interface UserAdminRepository extends JpaRepository<UserAdmin, Long> {
	UserAdmin findByUsername(String username);
}
