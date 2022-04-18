package com.shopme.shopmebackend.admin.user.service;

import com.shopme.shopmebackend.admin.user.exception.UserNotFoundException;
import com.shopme.shopmecommon.entity.Role;
import com.shopme.shopmecommon.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IUserService {

    List<User> listAll();
    Page<User> listByPage(int pageNumber);
    List<Role> listRoles();
    User save(User user);
    boolean isEmailUniqe(String email, Integer id);
    User getById(Integer id) throws UserNotFoundException;
    void deteteById(Integer id) throws UserNotFoundException;
    void updateUserEnabledStatus(Integer id, boolean status);
}
