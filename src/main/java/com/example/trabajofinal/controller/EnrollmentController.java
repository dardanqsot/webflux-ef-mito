package com.example.trabajofinal.controller;

import com.example.trabajofinal.dto.EnrollmentDto;
import com.example.trabajofinal.model.Enrollment;
import com.example.trabajofinal.service.IEnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final IEnrollmentService service;
    private final ModelMapper modelMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<EnrollmentDto>>> findAll() {
        Flux<EnrollmentDto> fx = service.findAll().map(c -> modelMapper.map(c, EnrollmentDto.class));

        return Mono.just(ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fx)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<EnrollmentDto>> findById(@PathVariable("id") String id) {
        return service.findById(id)
                .map(c -> modelMapper.map(c, EnrollmentDto.class))
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<EnrollmentDto>> save(@Valid @RequestBody EnrollmentDto dto, final ServerHttpRequest req) {
        return service.save(modelMapper.map(dto,  Enrollment.class))
                .map(e -> modelMapper.map(e,  EnrollmentDto.class))
                .map(e -> ResponseEntity.created(
                                        URI.create(req.getURI().toString().concat("/").concat(e.getId()))
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<EnrollmentDto>> update(@Valid @PathVariable("id") String id, @RequestBody EnrollmentDto dto) {
        return Mono.just(dto)
                .map(e -> {
                    e.setId(id);
                    return e;
                })
                .flatMap(e -> service.update(id, modelMapper.map(dto,  Enrollment.class)))
                .map(e -> modelMapper.map(e,  EnrollmentDto.class))
                .map(e -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Boolean>> delete(@PathVariable("id") String id) {
        return service.delete(id)
                .map(e -> ResponseEntity
                        .ok()
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
