package com.shopme.shopmebackend.admin.category;

import com.shopme.shopmebackend.admin.category.repository.CategoryRepository;
import com.shopme.shopmebackend.admin.category.service.impl.CategoryService;
import com.shopme.shopmecommon.entity.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository repository;

    @InjectMocks
    private CategoryService service;

    @Test
    public void checkUniqueInNewModeReturnDuplicateName() {
        Integer id = null;
        String name = "Smartphones";
        String alias = "abcd";

        Category category = new Category(id, name, alias);

        Mockito.when(repository.findByName(name)).thenReturn(category);
        Mockito.when(repository.findByAlias(alias)).thenReturn(null);

        String result = service.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("Duplicate name");
    }

    @Test
    public void checkUniqueInNewModeReturnDuplicateAlias() {
        Integer id = null;
        String name = "abcd";
        String alias = "Smartphone";

        Category category = new Category(id, name, alias);

        Mockito.when(repository.findByName(name)).thenReturn(null);
        Mockito.when(repository.findByAlias(alias)).thenReturn(category);

        String result = service.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("Duplicate alias");
    }

    @Test
    public void checkUniqueInNewModeReturnOK() {
        Integer id = null;
        String name = "abcd";
        String alias = "bfbsd";

        Mockito.when(repository.findByName(name)).thenReturn(null);
        Mockito.when(repository.findByAlias(alias)).thenReturn(null);

        String result = service.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("OK");
    }

    @Test
    public void checkUniqueInEditModeReturnDuplicateName() {
        Integer id = 1;
        String name = "Smartphones";
        String alias = "abcd";

        Category category = new Category(2, name, alias);

        Mockito.when(repository.findByName(name)).thenReturn(category);
        Mockito.when(repository.findByAlias(alias)).thenReturn(null);

        String result = service.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("Duplicate name");
    }

    @Test
    public void checkUniqueInEditModeReturnDuplicateAlias() {
        Integer id = 1;
        String name = "abcd";
        String alias = "Smartphone";

        Category category = new Category(2, name, alias);

        Mockito.when(repository.findByName(name)).thenReturn(null);
        Mockito.when(repository.findByAlias(alias)).thenReturn(category);

        String result = service.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("Duplicate alias");
    }

    @Test
    public void checkUniqueInEditModeReturnOK() {
        Integer id = 1;
        String name = "abcd";
        String alias = "bfbsd";

        Category category = new Category(id, name, alias);

        Mockito.when(repository.findByName(name)).thenReturn(null);  //null or category
        Mockito.when(repository.findByAlias(alias)).thenReturn(category);  //null or category

        String result = service.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("OK");
    }
}
