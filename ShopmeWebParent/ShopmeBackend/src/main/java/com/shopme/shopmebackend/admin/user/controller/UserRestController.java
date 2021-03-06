package com.shopme.shopmebackend.admin.user.controller;

import com.shopme.shopmebackend.admin.user.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    @Autowired
    private UserService userService;

    @PostMapping("/users/check_email")
    public String checkDuplicateEmail(@Param("email") String email, @Param("id") Integer id) {
        return userService.isEmailUniqe(email, id) ? "OK" : "Duplicated";
    }
}
