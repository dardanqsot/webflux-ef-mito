package com.example.trabajofinal.service.impl;


import com.example.trabajofinal.model.Student;
import com.example.trabajofinal.repo.IStudentRepo;
import com.example.trabajofinal.repo.IGenericRepo;
import com.example.trabajofinal.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends CRUDImpl<Student, String> implements IStudentService {

    private final IStudentRepo repo;

    @Override
    protected IGenericRepo<Student, String> getRepo() {
        return repo;
    }

}
