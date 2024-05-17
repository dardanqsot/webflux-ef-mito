package com.example.trabajofinal.handler;

import com.example.trabajofinal.dto.CourseDto;
import com.example.trabajofinal.model.Course;
import com.example.trabajofinal.service.ICourseService;
import com.example.trabajofinal.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@RequiredArgsConstructor
public class CourseHandler {


    private final ICourseService service;

    private final ModelMapper mapper;
    private final RequestValidator requestValidator;

    public Mono<ServerResponse> findAll(ServerRequest req) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll().map(this::convertToDto), CourseDto.class);
    }

    public Mono<ServerResponse> findById(ServerRequest req) {
        String id = req.pathVariable("id");

        return service.findById(id)
                .map(this::convertToDto)
                .flatMap(e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        Mono<CourseDto> monoCourseDTO = req.bodyToMono(CourseDto.class);
        
        return monoCourseDTO
                .flatMap(requestValidator::validate)
                .flatMap(e -> service.save(this.convertToDocument(e)))
                .map(this::convertToDto)
                .flatMap(e -> ServerResponse
                        .created(URI.create(req.uri().toString().concat("/").concat(e.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                );
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        String id = req.pathVariable("id");

        return req.bodyToMono(CourseDto.class)
                .map(e -> {
                    e.setId(id);
                    return e;
                })
                .flatMap(requestValidator::validate)
                .flatMap(e -> service.update(id, convertToDocument(e)))
                .map(this::convertToDto)
                .flatMap(e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        String id = req.pathVariable("id");

        return service.delete(id)
                .flatMap(result -> {
                    if (result) {
                        return ServerResponse.noContent().build();
                    } else {
                        return ServerResponse.notFound().build();
                    }
                });
    }

    private CourseDto convertToDto(Course model) {
        return mapper.map(model, CourseDto.class);
    }

    private Course convertToDocument(CourseDto dto) {
        return mapper.map(dto, Course.class);
    }
}
