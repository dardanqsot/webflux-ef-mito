package com.example.trabajofinal.service.impl;


import com.example.trabajofinal.model.Student;
import com.example.trabajofinal.repo.IStudentRepo;
import com.example.trabajofinal.repo.IGenericRepo;
import com.example.trabajofinal.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends CRUDImpl<Student, String> implements IStudentService {

    private final IStudentRepo repo;

    @Override
    protected IGenericRepo<Student, String> getRepo() {
        return repo;
    }

    @Override
    public Flux<Student> findAllOrderedByAge(String order) {
        Flux<Student> students = repo.findAll();
        if ("desc".equalsIgnoreCase(order)) {
            return students.sort((s1, s2) -> Integer.compare(s2.getAge(), s1.getAge()));
        } else {
            return students.sort((s1, s2) -> Integer.compare(s1.getAge(), s2.getAge()));
        }
    }
}
