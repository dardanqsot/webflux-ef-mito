package com.example.trabajofinal.config;


import com.example.trabajofinal.handler.CourseHandler;
import com.example.trabajofinal.handler.EnrollmentHandler;
import com.example.trabajofinal.handler.StudentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routesCourses(CourseHandler handler){
        return route(GET("/v2/courses"), handler::findAll)
                .andRoute(GET("/v2/courses/{id}"), handler::findById)
                .andRoute(POST("/v2/courses"), handler::create)
                .andRoute(PUT("/v2/courses/{id}"), handler::update)
                .andRoute(DELETE("/v2/courses/{id}"), handler::delete);

    }

    @Bean
    public RouterFunction<ServerResponse> routesStudents(StudentHandler handler){
        return route(GET("/v2/students"), handler::findAll)
                .andRoute(GET("/v2/students/{id}"), handler::findById)
                .andRoute(POST("/v2/students"), handler::create)
                .andRoute(PUT("/v2/students/{id}"), handler::update)
                .andRoute(DELETE("/v2/students/{id}"), handler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> routesEnrollements(EnrollmentHandler handler){
        return route(GET("/v2/enrollments"), handler::findAll)
                .andRoute(GET("/v2/enrollments/{id}"), handler::findById)
                .andRoute(POST("/v2/enrollments"), handler::create)
                .andRoute(PUT("/v2/enrollments/{id}"), handler::update)
                .andRoute(DELETE("/v2/enrollments/{id}"), handler::delete);
    }

}
