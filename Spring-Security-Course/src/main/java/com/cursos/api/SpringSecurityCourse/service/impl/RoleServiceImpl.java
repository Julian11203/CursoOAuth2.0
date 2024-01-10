package com.cursos.api.SpringSecurityCourse.service.impl;

import com.cursos.api.SpringSecurityCourse.persistence.entity.security.Role;
import com.cursos.api.SpringSecurityCourse.persistence.repository.security.RoleRepository;
import com.cursos.api.SpringSecurityCourse.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Value("${security.default.role}")
    private String defaultRole;
    @Override
    public Optional<Role> findDefaultRole() {
        return roleRepository.findByName("defaultRole");
    }
}
