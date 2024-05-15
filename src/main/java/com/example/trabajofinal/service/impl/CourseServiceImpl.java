package com.example.trabajofinal.service.impl;


import com.example.trabajofinal.model.Course;
import com.example.trabajofinal.repo.ICourseRepo;
import com.example.trabajofinal.repo.IGenericRepo;
import com.example.trabajofinal.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends CRUDImpl<Course, String> implements ICourseService {

    private final ICourseRepo repo;

    @Override
    protected IGenericRepo<Course, String> getRepo() {
        return repo;
    }

}
