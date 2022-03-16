package com.shopme.shopmebackend.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.shopme.shopmecommon.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

//    @Test
//    public void testCreateFirstRole() {
//        Role roleAdmin = new Role("Admin", "manage everything");
//        Role saveRole = roleRepository.save(roleAdmin);
//
//        assertThat(saveRole.getId()).isGreaterThan(0);
//    }

    @Test
    public void testCreateRestRoles() {
        Role roleAdmin = new Role("Admin", "manage everything");
        Role roleSalePerson = new Role("Salesperson", "manage product price, " +
                "customer, shipping, orders and sales report");
        Role roleEditer = new Role("Editor", "manage categories, brands, " +
                "products, articles and menus");
        Role roleShipper = new Role("Shipper", "view products, view orders, " +
                "and update orders status");
        Role roleAssistant = new Role("Assistant", "manage questions and views");

        roleRepository.saveAll(List.of(roleAdmin,roleSalePerson, roleEditer, roleShipper, roleAssistant));
    }
}
