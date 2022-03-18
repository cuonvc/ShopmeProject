package com.shopme.shopmebackend.admin.user;

import com.shopme.shopmebackend.admin.user.repository.UserRepository;
import com.shopme.shopmecommon.entity.Role;
import com.shopme.shopmecommon.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        User userCuong = new User("cuongnvc@gmail.com", "123", "Cuong", "Nguyen Van");
        userCuong.addRole(roleAdmin);

        User savedUser = userRepository.save(userCuong);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWithTwoRoles() {
        User userCuongFake = new User("fakenvc5802@gmail.com", "abc", "Cuong", "Fake");
        Role roleEditer = new Role(3);
        Role roleAssistant = new Role(5);

        userCuongFake.addRole(roleEditer);
        userCuongFake.addRole(roleAssistant);

        User savedUser = userRepository.save(userCuongFake);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> userList = userRepository.findAll();
        userList.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById() {
        User user = userRepository.findById(1).get();
        System.out.println(user);
    }

    @Test
    public void testUpdateUserDetails() {
        User user = userRepository.findById(2).get();

        user.setEmail("fakenvc5802@gmail.com");
        user.setEnabled(false);
    }

    @Test
    public void testUpdateUserRole() {
        User user = userRepository.findById(1).get();
        Role oldRole = new Role(2);
        Role newRole = new Role(3);

        user.getRoles().remove(oldRole);
        user.addRole(newRole);

        userRepository.save(user);
    }

    @Test
    public void testDeleteUser() {
        Integer userId = 3;
        userRepository.deleteById(userId);
    }
}
