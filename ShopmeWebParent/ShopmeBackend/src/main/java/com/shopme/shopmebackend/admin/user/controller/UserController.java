package com.shopme.shopmebackend.admin.user.controller;

import com.shopme.shopmebackend.admin.FileUploadUtil;
import com.shopme.shopmebackend.admin.user.exception.UserNotFoundException;
import com.shopme.shopmebackend.admin.user.repository.UserRepository;
import com.shopme.shopmebackend.admin.user.service.impl.UserService;
import com.shopme.shopmecommon.entity.Role;
import com.shopme.shopmecommon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    private String defaultRedirectURL = "redirect:/users/page/1?sortField=firstName&sortDir=asc";

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listAll(Model model) {
        List<User> listUsers = userService.listAll();
        model.addAttribute("listUsers", listUsers);
        return "users";  //users.html
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        User user = new User();
        List<Role> listRoles = userService.listRoles();

        user.setEnabled(true);
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("pageTitle", "Create new user");
        return "user_form";
    }

    @PostMapping("users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes,
                           //"image" match with name="image" in user_form.html (col ~ 133)
                           @RequestParam("image")MultipartFile multipartFile) throws IOException {

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhotos(fileName);
            User savedUser = userService.save(user);

            String uploadDir = "user-photos/" + savedUser.getId();  //set directory of photo is "id" of user had saved

            //Before replacing the new photo (saveFile), it needs to be delete old photo (cleanDir)
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (user.getPhotos().isEmpty()) {
                user.setPhotos(null);
            }
            userService.save(user);
            redirectAttributes.addFlashAttribute("message", "Tạo user thành công. .");
        }

        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        try {
            User user = userService.getById(id);
            List<Role> listRoles = userService.listRoles();
            model.addAttribute("user", user);  //"user" -> user_form (line 72)
            model.addAttribute("pageTitle", "Update user (ID: " + id + ")");
            model.addAttribute("listRoles", listRoles);
            return "user_form";
        } catch (UserNotFoundException exception) {
            redirectAttributes.addFlashAttribute("message", exception.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getById(id);
            userService.deteteById(id);
            redirectAttributes.addFlashAttribute("message",
                    "User " + user.getEmail() + " has been deleted successfully.");
        } catch (UserNotFoundException exception) {
            redirectAttributes.addFlashAttribute("message", exception.getMessage());
        }

        return "redirect:/users";
    }

    @GetMapping("/users/{id}/enabled/{status}")
    public String enabledStatusUser(@PathVariable("id") Integer id,
                    @PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getById(id);
            userService.updateUserEnabledStatus(id, enabled);

            String status = enabled ? "enable" : "disable";
            String message = "The user " + user.getEmail() + " has been " + status;
            redirectAttributes.addFlashAttribute("message", message);
        } catch (UserNotFoundException exception) {
            //exception of userService.getById();
            redirectAttributes.addFlashAttribute("message", exception.getMessage());
        }

        return "redirect:/users";
    }

}
