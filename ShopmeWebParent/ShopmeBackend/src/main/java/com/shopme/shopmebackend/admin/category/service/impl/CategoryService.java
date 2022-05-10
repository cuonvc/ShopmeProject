package com.shopme.shopmebackend.admin.category.service.impl;

import com.shopme.shopmebackend.admin.category.exception.CategoryException;
import com.shopme.shopmebackend.admin.category.repository.CategoryRepository;
import com.shopme.shopmebackend.admin.category.service.ICategoryService;
import com.shopme.shopmecommon.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository repository;

    @Override
    public List<Category> listAll() {
        List<Category> rootCategories = repository.findCategoryRoot();
        return listHierarchicalCategories(rootCategories);
    }

    private List<Category> listHierarchicalCategories(List<Category> rootList) {
        List<Category> hierarchicalCategories = new ArrayList<>();

        for (Category rootCategory : rootList) {
            hierarchicalCategories.add(Category.copyFull(rootCategory));

            Set<Category> children = rootCategory.getChildren();

            for (Category subCategory : children) {
                String name = "--" + subCategory.getName();

                hierarchicalCategories.add(Category.copyFull(subCategory, name));

                listHierarchicalCategories(hierarchicalCategories, subCategory, 1);
            }
        }

        return hierarchicalCategories;
    }

    private void listHierarchicalCategories(List<Category> hierarchicalList,
                                            Category parent, int subLevel) {
        Set<Category> children = parent.getChildren();
        int newSubLevel = subLevel + 1;

        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }

            name += subCategory.getName();

            hierarchicalList.add(Category.copyFull(subCategory, name));

            listHierarchicalCategories(hierarchicalList, subCategory, newSubLevel);
        }

    }

    @Override
    public List<Category> listCategoriesUsedInForm() {
        List<Category> categoriesUsedInForm = new ArrayList<>();

        Iterable<Category> categoriesInDB = repository.findAll();

        for (Category category : categoriesInDB) {
            if (category.getParent() == null) {

                categoriesUsedInForm.add(Category.copyIdAndName(category));

                Set<Category> children = category.getChildren();

                for (Category subCategory : children) {
                    String name = "--" + subCategory.getName();

                    categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));

                    listChildren(categoriesUsedInForm, subCategory, 1);
                }
            }
        }

        return categoriesUsedInForm;
    }

    @Override
    public Category getById(Integer id) throws CategoryException {
        try {
            return repository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new CategoryException("Không tìm thấy id: " + id);
        }
    }

    @Override
    public Category save(Category category) {
        return repository.save(category);
    }

    private void listChildren(List<Category> categoriesUsedInForm, Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            name += subCategory.getName();

            categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));

            listChildren(categoriesUsedInForm, subCategory, newSubLevel);
        }
    }


}
