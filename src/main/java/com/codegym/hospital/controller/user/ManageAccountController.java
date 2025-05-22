package com.codegym.hospital.controller.user;

import com.codegym.hospital.model.user.User;
import com.codegym.hospital.service.IDoctorService;
import com.codegym.hospital.service.IEmailService;
import com.codegym.hospital.service.IPatientService;
import com.codegym.hospital.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/manageAccount")
public class ManageAccountController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IEmailService emailService;

    @GetMapping("listUser")
    public String showListUser(Model model) {
        List<User> userList = new ArrayList<>();
        userList.addAll(userService.getUserByStatus("inactive"));
        userList.addAll(userService.getUserByStatus("active"));
        model.addAttribute("users", userList);
        return "admin/manageAccount/list-users";
    }


    @GetMapping("approveUser")
    public String test(Model model) {
        model.addAttribute("users", userService.getUserByStatus("pending"));
        return "admin/manageAccount/approve-users";
    }


    @PostMapping("/approveUser/{action}")
    public ResponseEntity<String> approveUser(
            @RequestParam("id") Long id,
            @RequestParam(value = "reason", required = false) String reasonFromParam,
            @RequestBody(required = false) Map<String, String> payload,
            @PathVariable String action) {

        Optional<User> optionalUser = userService.getUserById(id);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.badRequest().body("Không tìm thấy user");
        }
        User user = optionalUser.get();

        if (action.equals("accept")) {
            user.setStatus("active");
            emailService.sendApprovalNotificationEmail(user.getEmail());
            userService.saveUser(user);
        } else if (action.equals("reject")) {
            String reason = (payload != null && payload.get("reason") != null)
                    ? payload.get("reason")
                    : (reasonFromParam != null ? reasonFromParam : "Không có lý do cụ thể.");

            emailService.sendRejectNotificationEmail(user.getEmail(), reason);
            userService.deleteUser(user.getId());
        } else {
            return ResponseEntity.badRequest().body("Trạng thái không hợp lệ");
        }
        return ResponseEntity.ok("");
    }


}

