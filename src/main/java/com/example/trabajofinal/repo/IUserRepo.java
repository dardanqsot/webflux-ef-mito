package com.example.trabajofinal.repo;

import com.example.trabajofinal.model.User;
import reactor.core.publisher.Mono;

public interface IUserRepo extends IGenericRepo<User, String>{

    Mono<User> findOneByUsername(String username);
}
