package com.shopme.shopmebackend.admin.user.service;

import com.shopme.shopmecommon.entity.Role;
import com.shopme.shopmecommon.entity.User;

import java.util.List;

public interface IUserService {

    List<User> listAll();
    List<Role> listRoles();
    void save(User user);
}
