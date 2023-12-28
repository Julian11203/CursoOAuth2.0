package com.cursos.api.SpringSecurityCourse.persistence.repository;

import com.cursos.api.SpringSecurityCourse.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
