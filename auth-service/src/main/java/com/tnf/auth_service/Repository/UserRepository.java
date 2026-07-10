package com.tnf.auth_service.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tnf.auth_service.Entity.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
}
