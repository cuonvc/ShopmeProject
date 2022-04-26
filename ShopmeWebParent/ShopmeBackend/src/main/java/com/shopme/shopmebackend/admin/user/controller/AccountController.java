package com.shopme.shopmebackend.admin.user.controller;

import com.shopme.shopmebackend.admin.FileUploadUtil;
import com.shopme.shopmebackend.admin.security.ShopmeUserDetails;
import com.shopme.shopmebackend.admin.user.service.impl.UserService;
import com.shopme.shopmecommon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/account")
    public String viewDetails(@AuthenticationPrincipal ShopmeUserDetails loggedUser,
                              Model model) {
        String email = loggedUser.getUsername();
        User user = userService.getByEmail(email);
        model.addAttribute("user", user);

        return "account_form";
    }

    @PostMapping("/account/update")
    public String saveDetails(User user, RedirectAttributes redirectAttributes,
                              @RequestParam ("image") MultipartFile multipartFile,
                              @AuthenticationPrincipal ShopmeUserDetails loggedUser) throws IOException {

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhotos(fileName);
            User savedUser = userService.updateAccount(user);
            String uploadDir = "user-photos/" + savedUser.getId();  //set directory of photo is "id" of user had saved

            //Before replacing the new photo (saveFile), it needs to be delete old photo (cleanDir)
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (user.getPhotos().isEmpty()) {
                user.setPhotos(null);
            }
            userService.updateAccount(user);

        }

        loggedUser.setFirstName(user.getFirstName());
        loggedUser.setLastName(user.getLastName());

        redirectAttributes.addFlashAttribute("message", "Your account has been updated successfully!");

        return "redirect:/account";
    }
}
