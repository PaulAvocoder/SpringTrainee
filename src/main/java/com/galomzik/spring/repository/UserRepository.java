package com.galomzik.spring.repository;

import com.galomzik.spring.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAll();
    UserEntity findByUsername(String username);

}
