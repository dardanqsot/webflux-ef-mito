package com.example.trabajofinal.service;

import com.example.trabajofinal.model.User;
import reactor.core.publisher.Mono;

public interface IUserService extends ICRUD<User, String>{

    Mono<User> saveHash(User user);
    Mono<com.example.trabajofinal.security.User> searchByUser(String username);
}
