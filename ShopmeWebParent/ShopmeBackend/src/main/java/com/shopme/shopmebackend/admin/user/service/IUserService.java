package com.shopme.shopmebackend.admin.user.service;

import com.shopme.shopmebackend.admin.user.exception.UserNotFoundException;
import com.shopme.shopmecommon.entity.Role;
import com.shopme.shopmecommon.entity.User;

import java.util.List;

public interface IUserService {

    List<User> listAll();
    List<Role> listRoles();
    void save(User user);
    boolean isEmailUniqe(String email, Integer id);
    User getById(Integer id) throws UserNotFoundException;
    void deteteById(Integer id) throws UserNotFoundException;
    void updateUserEnabledStatus(Integer id, boolean status);
}
