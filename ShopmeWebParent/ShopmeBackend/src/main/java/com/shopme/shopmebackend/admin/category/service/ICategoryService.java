package com.shopme.shopmebackend.admin.category.service;

import com.shopme.shopmecommon.entity.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> listAll();
    List<Category> listCategoriesUsedInForm();
    Category save(Category category);
}
