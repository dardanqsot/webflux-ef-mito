package com.example.trabajofinal.service.impl;

import com.example.trabajofinal.repo.IGenericRepo;
import com.example.trabajofinal.service.ICRUD;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

    protected abstract IGenericRepo<T, ID> getRepo();

    @Override
    public Mono<T> save(T t) {
        return getRepo().save(t);
    }

    @Override
    public Mono<T> update(ID id, T t) {
        return getRepo().findById(id).flatMap(e -> getRepo().save(t));
    }

    @Override
    public Flux<T> findAll() {
        return getRepo().findAll();
    }

    @Override
    public Mono<T> findById(ID id) {
        return getRepo().findById(id);
    }

    @Override
    public Mono<Boolean> delete(ID id) {
        return getRepo().findById(id)
                .hasElement()
                .flatMap(result -> {
                    if(result){
                        return getRepo().deleteById(id).thenReturn(true);
                    }else{
                        return Mono.just(false);
                    }
                });
    }


}
