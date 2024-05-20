package com.example.trabajofinal.service;

import com.example.trabajofinal.model.Student;
import reactor.core.publisher.Flux;

public interface IStudentService extends ICRUD<Student, String>{

    Flux<Student> findAllOrderedByAge(String order);
}
