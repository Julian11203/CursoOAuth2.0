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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryServices categoryServices;
    @GetMapping
    public ResponseEntity<Page<Category>> findAll(Pageable pageable){
        Page<Category> categoriesPage = categoryServices.findAll(pageable);
        if(categoriesPage.hasContent()){
            return ResponseEntity.ok(categoriesPage);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> findOneById(@PathVariable Long categoryId){
        Optional<Category> category = categoryServices.findOneById(categoryId);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Category> createOne(@RequestBody @Valid SaveCategory saveCategory){
        Category category = categoryServices.createOne(saveCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateOneById(@PathVariable Long categoryId, @RequestBody @Valid SaveCategory saveCategory){
        Category category = categoryServices.updateOneById(categoryId, saveCategory);
        return ResponseEntity.ok(category);
    }
    @PutMapping("/{categoryId}/disabled")
    public ResponseEntity<Category> disableOneById(@PathVariable Long categoryId){
        Category category = categoryServices.disableOneById(categoryId);
        return ResponseEntity.ok(category);
    }

}
