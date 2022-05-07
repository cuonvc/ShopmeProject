package com.shopme.shopmebackend.admin.category.controller;

import com.shopme.shopmebackend.admin.FileUploadUtil;
import com.shopme.shopmebackend.admin.category.service.impl.CategoryService;
import com.shopme.shopmecommon.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
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

    @PostMapping("/categories/save")
    public String saveCategory(Category category, RedirectAttributes redirectAttributes,
                               @RequestParam("fileImage")MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            category.setImage(fileName);
            Category categorySaved = service.save(category);
            String uploadDir = "../category-images/" + categorySaved.getId();  //folder of ShopmeWebParent

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (category.getImage().isEmpty()) {
                category.setImage(null);
            }
            service.save(category);
        }

        service.save(category);

        redirectAttributes.addFlashAttribute("message", "Update thể loại thành công!");

        return "redirect:/categories";
    }
}
