package com.senkosun.antihack_barapi.service;

import com.senkosun.antihack_barapi.entity.User;
import com.senkosun.antihack_barapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    @Transactional
    @Override
    public User registerUser() {
        String token = generateToken();
        User user = User.builder().token(token).build();

        userRepository.save(user);
        log.info("Новый пользователь зарегистрирован: id={}, token={}",
                user.getId(), user.getToken());
        return user;
    }

    @Override
    public Optional<User> findByToken(String token) {
        return userRepository.findByToken(token);
    }

    @Override
    public String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
