package com.cursos.api.SpringSecurityCourse.service;

import com.cursos.api.SpringSecurityCourse.dto.SaveUser;
import com.cursos.api.SpringSecurityCourse.persistence.entity.User;

public interface UserService {
    User registerOneCustomer(SaveUser newUser);
}
