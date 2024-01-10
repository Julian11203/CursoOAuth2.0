package com.cursos.api.SpringSecurityCourse.persistence.repository.security;

import com.cursos.api.SpringSecurityCourse.persistence.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String defaultRole);
}
