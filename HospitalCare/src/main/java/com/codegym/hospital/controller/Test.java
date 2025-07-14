package com.codegym.hospital.controller;

import com.codegym.hospital.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping
public class Test {
    @Autowired
    public IUserService userService;

    @GetMapping
    public String test() {
        return "test";
    }
    @PostMapping("test")
    public String post(@RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile, Model model) throws IOException {
        String filename = userService.uploadFile(avatarFile);
        model.addAttribute("result", filename);
        return "test";
    }
}
