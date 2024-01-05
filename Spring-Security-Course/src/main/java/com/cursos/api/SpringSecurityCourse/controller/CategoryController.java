package com.cursos.api.SpringSecurityCourse.controller;

import com.cursos.api.SpringSecurityCourse.dto.SaveCategory;
import com.cursos.api.SpringSecurityCourse.dto.SaveProduct;
import com.cursos.api.SpringSecurityCourse.persistence.entity.Category;
import com.cursos.api.SpringSecurityCourse.persistence.entity.Product;
import com.cursos.api.SpringSecurityCourse.service.CategoryServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryServices categoryServices;
    @PreAuthorize("hasAnyAuthority('READ_ALL_CATEGORIES')")
    @GetMapping
    public ResponseEntity<Page<Category>> findAll(Pageable pageable){
        Page<Category> categoriesPage = categoryServices.findAll(pageable);
        if(categoriesPage.hasContent()){
            return ResponseEntity.ok(categoriesPage);
        }
        return ResponseEntity.notFound().build();
    }
    @PreAuthorize("hasAnyAuthority('READ_ONE_CATEGORY')")
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> findOneById(@PathVariable Long categoryId){
        Optional<Category> category = categoryServices.findOneById(categoryId);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PreAuthorize("hasAnyAuthority('CREATE_ONE_CATEGORY')")
    @PostMapping
    public ResponseEntity<Category> createOne(@RequestBody @Valid SaveCategory saveCategory){
        Category category = categoryServices.createOne(saveCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
    @PreAuthorize("hasAnyAuthority('UPDATE_ONE_CATEGORY')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateOneById(@PathVariable Long categoryId, @RequestBody @Valid SaveCategory saveCategory){
        Category category = categoryServices.updateOneById(categoryId, saveCategory);
        return ResponseEntity.ok(category);
    }
    @PreAuthorize("hasAnyAuthority('DISABLE_ONE_CATEGORY')")
    @PutMapping("/{categoryId}/disabled")
    public ResponseEntity<Category> disableOneById(@PathVariable Long categoryId){
        Category category = categoryServices.disableOneById(categoryId);
        return ResponseEntity.ok(category);
    }

}
