package com.shopme.shopmebackend.admin.category.controller;

import com.shopme.shopmebackend.admin.category.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryRestController {

    @Autowired
    private CategoryService service;

    @PostMapping("/categories/check_unique")
    public String checkUnique(@Param("id") Integer id, String name, String alias) {

        return service.checkUnique(id, name, alias);
    }
}
