package com.example.trabajofinal.service.impl;


import com.example.trabajofinal.model.Enrollment;
import com.example.trabajofinal.repo.IEnrollmentRepo;
import com.example.trabajofinal.repo.IGenericRepo;
import com.example.trabajofinal.service.IEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl extends CRUDImpl<Enrollment, String> implements IEnrollmentService {

    private final IEnrollmentRepo repo;
    
    @Override
    protected IGenericRepo<Enrollment, String> getRepo() {
        return repo;
    }

}
