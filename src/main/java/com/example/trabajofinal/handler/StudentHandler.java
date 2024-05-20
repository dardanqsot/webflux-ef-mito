package com.example.trabajofinal.handler;

import com.example.trabajofinal.dto.StudentDto;
import com.example.trabajofinal.model.Student;
import com.example.trabajofinal.service.IStudentService;
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
public class StudentHandler {


    private final IStudentService service;

    private final ModelMapper mapper;
    //private final Validator validator;
    private final RequestValidator requestValidator;

    public Mono<ServerResponse> findAll(ServerRequest req) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll().map(this::convertToDto), StudentDto.class);
    }

    public Mono<ServerResponse> findAllOrderedByAge(ServerRequest req) {
        String order = req.queryParam("order").orElse("asc");

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAllOrderedByAge(order).map(this::convertToDto), StudentDto.class);
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
        Mono<StudentDto> monoStudentDTO = req.bodyToMono(StudentDto.class);
        
        return monoStudentDTO
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

        return req.bodyToMono(StudentDto.class)
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

    private StudentDto convertToDto(Student model) {
        return mapper.map(model, StudentDto.class);
    }

    private Student convertToDocument(StudentDto dto) {
        return mapper.map(dto, Student.class);
    }
}
