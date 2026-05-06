package com.senkosun.antihack_barapi.service;
import com.senkosun.antihack_barapi.entity.User;
import java.util.Optional;

public interface AuthService {
    User registerUser();

    User resetUser(User user);

    String generateToken();

    Optional<User> findByToken(String token);


}
