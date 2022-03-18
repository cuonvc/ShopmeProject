package com.shopme.shopmebackend.admin.user.service.impl;

import com.shopme.shopmebackend.admin.user.repository.RoleRepository;
import com.shopme.shopmebackend.admin.user.repository.UserRepository;
import com.shopme.shopmebackend.admin.user.service.IUserService;
import com.shopme.shopmecommon.entity.Role;
import com.shopme.shopmecommon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public List<User> listAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public List<Role> listRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
