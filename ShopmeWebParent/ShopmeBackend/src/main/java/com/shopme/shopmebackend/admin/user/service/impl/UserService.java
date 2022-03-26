package com.shopme.shopmebackend.admin.user.service.impl;

import com.shopme.shopmebackend.admin.user.exception.UserNotFoundException;
import com.shopme.shopmebackend.admin.user.repository.RoleRepository;
import com.shopme.shopmebackend.admin.user.repository.UserRepository;
import com.shopme.shopmebackend.admin.user.service.IUserService;
import com.shopme.shopmecommon.entity.Role;
import com.shopme.shopmecommon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
        boolean isUpdatingUser = user.getId() != null;
        if (isUpdatingUser) {
            User existingUser = userRepository.findById(user.getId()).get();
            if (user.getPassWord().isEmpty()) {
                user.setPassWord(existingUser.getPassWord());
            } else {
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }

        userRepository.save(user);
    }

    @Override
    public boolean isEmailUniqe(String email, Integer id) {
        User userByEmail = userRepository.getUserByEmail(email);

        if (userByEmail == null) {
            return true;
        }

        boolean isCreatingNew = (id == null);
        if (isCreatingNew) {
            if (userByEmail != null) {
                return false;
            }
        } else {
            if (userByEmail.getId() != id) {
                return false;
            }
        }

        return true;
    }

    @Override
    public User getById(Integer id) throws UserNotFoundException {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException exception) {
            throw new UserNotFoundException("Could not find any user by id: " + id);
        }
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassWord());
        user.setPassWord(encodedPassword);
    }


}
