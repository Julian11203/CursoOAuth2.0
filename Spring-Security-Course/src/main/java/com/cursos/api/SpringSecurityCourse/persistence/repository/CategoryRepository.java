package com.cursos.api.SpringSecurityCourse.persistence.repository;

import com.cursos.api.SpringSecurityCourse.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
