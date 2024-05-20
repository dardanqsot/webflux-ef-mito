package com.example.trabajofinal.controller;

import com.example.trabajofinal.dto.StudentDto;
import com.example.trabajofinal.model.Student;
import com.example.trabajofinal.service.IStudentService;
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
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final IStudentService service;
    private final ModelMapper modelMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<StudentDto>>> findAll() {
        Flux<StudentDto> fx = service.findAll().map(c -> modelMapper.map(c, StudentDto.class));

        return Mono.just(ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fx)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/ordered-by-age")
    public Mono<ResponseEntity<Flux<StudentDto>>> findAllOrderedByAge(@RequestParam(defaultValue = "asc") String order) {
        Flux<StudentDto> fx = service.findAllOrderedByAge(order).map(s -> modelMapper.map(s, StudentDto.class));

        return Mono.just(ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fx)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<StudentDto>> findById(@PathVariable("id") String id) {
        return service.findById(id)
                .map(c -> modelMapper.map(c, StudentDto.class))
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<StudentDto>> save(@Valid @RequestBody StudentDto dto, final ServerHttpRequest req) {
        return service.save(modelMapper.map(dto,  Student.class))
                .map(e -> modelMapper.map(e,  StudentDto.class))
                .map(e -> ResponseEntity.created(
                                        URI.create(req.getURI().toString().concat("/").concat(e.getId()))
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<StudentDto>> update(@Valid @PathVariable("id") String id, @RequestBody StudentDto dto) {
        return Mono.just(dto)
                .map(e -> {
                    e.setId(id);
                    return e;
                })
                .flatMap(e -> service.update(id, modelMapper.map(dto,  Student.class)))
                .map(e -> modelMapper.map(e,  StudentDto.class))
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
