package com.shopme.shopmebackend.admin.user.service.impl;

import com.shopme.shopmebackend.admin.user.exception.UserNotFoundException;
import com.shopme.shopmebackend.admin.user.repository.RoleRepository;
import com.shopme.shopmebackend.admin.user.repository.UserRepository;
import com.shopme.shopmebackend.admin.user.service.IUserService;
import com.shopme.shopmecommon.entity.Role;
import com.shopme.shopmecommon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService implements IUserService {

    public static final int USER_PER_PAGE = 4;

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
    public Page<User> listByPage(int pageNumber, String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);
        if (sortDir.equals("asc")) {
            sort = sort.ascending();
        } else {
            sort = sort.descending();
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, USER_PER_PAGE, sort);
        return userRepository.findAll(pageable);
    }

    @Override
    public List<Role> listRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public User save(User user) {
        boolean isUpdatingUser = (user.getId() != null);
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

        return userRepository.save(user);
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

    @Override
    public void deteteById(Integer id) throws UserNotFoundException {
        Long countById = userRepository.countById(id);
        if (countById == null || countById == 0) {
            throw new UserNotFoundException("Could not find any user by id: " + id);
        } else {
            userRepository.deleteById(id);
        }
    }

    @Override
    public void updateUserEnabledStatus(Integer id, boolean enabled) {
        userRepository.updateEnabledStatus(id, enabled);
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassWord());
        user.setPassWord(encodedPassword);
    }


}
