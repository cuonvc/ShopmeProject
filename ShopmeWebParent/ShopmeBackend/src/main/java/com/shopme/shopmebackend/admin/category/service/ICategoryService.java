package com.shopme.shopmebackend.admin.category.service;

import com.shopme.shopmebackend.admin.category.exception.CategoryException;
import com.shopme.shopmecommon.entity.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> listAll();
    List<Category> listCategoriesUsedInForm();
    Category getById(Integer id) throws CategoryException;
    Category save(Category category);
    String checkUnique(Integer id, String name, String alias);
}
