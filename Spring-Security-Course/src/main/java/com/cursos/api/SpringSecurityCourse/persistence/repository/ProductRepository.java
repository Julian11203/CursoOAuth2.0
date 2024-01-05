package com.cursos.api.SpringSecurityCourse.persistence.repository;

import com.cursos.api.SpringSecurityCourse.persistence.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @PreAuthorize("hasAnyAuthority('READ_ALL_PRODUCTS')")
    Page<Product> findAll (Pageable pageable);
}
