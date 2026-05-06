package com.senkosun.antihack_barapi.service;
import com.senkosun.antihack_barapi.entity.User;
import java.util.Optional;

public interface AuthService {
    User registerUser();

    String generateToken();

    Optional<User> findByToken(String token);


}
