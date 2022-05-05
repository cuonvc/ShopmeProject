package com.shopme.shopmebackend.admin.category.controller;

import com.shopme.shopmebackend.admin.category.service.impl.CategoryService;
import com.shopme.shopmecommon.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("/categories")
    public String listCategories(Model model) {
        List<Category> categoryList = service.listAll();
        model.addAttribute("categoryList", categoryList);
        return "category/categories";
    }

    @GetMapping("/categories/new")
    public String newCategory(Model model) {
        List<Category> categoryList = service.listCategoriesUsedInForm();

        model.addAttribute("category", new Category());
        model.addAttribute("pageTitle", "Create new category");
        model.addAttribute("categoryList", categoryList);

        return "category/category_form";
    }
}
