package com.cursos.api.SpringSecurityCourse.service;

import com.cursos.api.SpringSecurityCourse.dto.SaveUser;
import com.cursos.api.SpringSecurityCourse.persistence.entity.security.User;

import java.util.Optional;

public interface UserService {
    User registerOneCustomer(SaveUser newUser);
    Optional<User> findOneByUsername (String username);
}
