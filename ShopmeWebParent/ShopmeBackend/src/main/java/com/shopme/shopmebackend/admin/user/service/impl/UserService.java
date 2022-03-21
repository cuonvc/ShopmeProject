package com.shopme.shopmebackend.admin.user.service.impl;

import com.shopme.shopmebackend.admin.user.repository.RoleRepository;
import com.shopme.shopmebackend.admin.user.repository.UserRepository;
import com.shopme.shopmebackend.admin.user.service.IUserService;
import com.shopme.shopmecommon.entity.Role;
import com.shopme.shopmecommon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


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
        encodePassword(user);
        System.out.println("========================================================");
        System.out.println(user.getPassWord());
        userRepository.save(user);
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassWord());
        user.setPassWord(encodedPassword);
    }

    public boolean isEmailUniqe(String email) {
        User userByEmail = userRepository.getUserByEmail(email);
        return userByEmail == null;  //user is not exist yet
    }
}
