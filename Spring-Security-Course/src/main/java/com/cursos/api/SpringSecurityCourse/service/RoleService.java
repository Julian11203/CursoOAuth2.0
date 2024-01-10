package com.cursos.api.SpringSecurityCourse.service;

import com.cursos.api.SpringSecurityCourse.persistence.entity.security.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findDefaultRole();
}
