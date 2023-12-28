package com.cursos.api.SpringSecurityCourse.service;

import com.cursos.api.SpringSecurityCourse.dto.SaveCategory;
import com.cursos.api.SpringSecurityCourse.persistence.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryServices {
    Page<Category> findAll(Pageable pageable);

    Optional<Category> findOneById(Long categoryId);

    Category createOne(SaveCategory saveCategory);

    Category updateOneById(Long categoryId, SaveCategory saveCategory);

    Category disableOneById(Long categoryId);
}
