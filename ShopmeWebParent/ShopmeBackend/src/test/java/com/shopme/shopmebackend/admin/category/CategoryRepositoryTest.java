package com.shopme.shopmebackend.admin.category;

import com.shopme.shopmebackend.admin.category.repository.CategoryRepository;
import com.shopme.shopmecommon.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void createRootCategory() {
        Category category = new Category("Electrolic", "electrolic", "default.png");
        categoryRepository.save(category);
    }

    @Test
    public void createSubCategory() {
        Category categoryParent = new Category(1);
        Category subCategory = new Category(categoryParent, "Gaming Computer", "Gaming Computer", "default.png");
        categoryRepository.save(subCategory);
    }

    @Test
    public void createSubCategories() {
        Category electrolic = new Category(3);
        Category smartphone = new Category(2);
        Category subElecttrolic = new Category(electrolic, "Air condition", "air condition", "default.png");
        Category subSmartphone = new Category(smartphone, "Gaming smartphone", "Gaming smartphone", "default.png");
        categoryRepository.saveAll(List.of(subElecttrolic, subSmartphone));
    }

    @Test
    public void getCategory() {
        Category category = categoryRepository.findById(2).get();
        System.out.println(category.getName());  //root category

        Set<Category> childrens = category.getChildren();
        for (Category subCategory : childrens) {
            System.out.println(subCategory.getName());  //sub category
        }
    }

    @Test
    public void listCategoryRoot() {
        List<Category> categoryList = categoryRepository.findCategoryRoot();
        categoryList.forEach(c -> System.out.println(c.getName()));
    }

    @Test
    public void testFindByName() {
        String name = "Computers";  //test pass
        String name1 = "Computer"; //test fail
        Category category = categoryRepository.findByName(name);

        assertThat(category).isNotNull();
    }

    @Test
    public void testFindByAlias() {
        String alias = "Smartphone";
        Category category = categoryRepository.findByAlias(alias);

        assertThat(category).isNotNull();
    }
}
