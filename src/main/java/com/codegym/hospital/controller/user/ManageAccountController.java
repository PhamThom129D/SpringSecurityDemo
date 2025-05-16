package com.codegym.hospital.controller.user;

import com.codegym.hospital.model.user.User;
import com.codegym.hospital.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(value = "/manageAccount")
public class ManageAccountController {
    @Autowired
    private IUserService userService;

    @GetMapping("approveUser")
    public String test(Model model) {
        model.addAttribute("users", userService.getUserByStatus("pending"));
        return "admin/manageAccount/approve-users";
    }

    @PostMapping("/approveUser/{action}")
    public ResponseEntity<String> approveUser(@RequestParam("id") Long id, @PathVariable String action){
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Không tìm thấy user");
        }
        User user = optionalUser.get();
        if (action.equals("accept")) {
            user.setStatus("active");
        } else if (action.equals("reject")) {
            user.setStatus("inactive");
        } else {
            return ResponseEntity.badRequest().body("Trạng thái không hợp lệ");
        }

        userService.saveUser(user);
        return ResponseEntity.ok("");
    }


}

