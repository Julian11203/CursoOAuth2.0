package com.cursos.api.SpringSecurityCourse.dto;

import java.io.Serializable;

public class ShowPermission implements Serializable {
    private Long id;
    private String operation;
    private String module;
    private  String role;
}
