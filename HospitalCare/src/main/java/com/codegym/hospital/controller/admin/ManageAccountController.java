package com.codegym.hospital.controller.admin;


import com.codegym.hospital.model.user.Doctors;
import com.codegym.hospital.model.user.Patients;
import com.codegym.hospital.model.user.User;
import com.codegym.hospital.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/manage-account")
public class ManageAccountController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IEmailService emailService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IDeparmentService deparmentService;
    @Autowired
    private IDoctorService doctorService;


    @GetMapping("/list-user")
    public String showListUser(Model model) {
        List<User> userList = new ArrayList<>();
        userList.addAll(userService.getUserByStatus("inactive"));
        userList.addAll(userService.getUserByStatus("active"));
        model.addAttribute("roleDisplay", roleService.getRoleDisplayNames());
        model.addAttribute("users", userList);
        return "admin/manageAccount/list_users";
    }
    @GetMapping("/approve-user")
    public String test(Model model) {
        model.addAttribute("users", userService.getUserByStatus("pending"));
        return "admin/manageAccount/approve_users";
    }

    @PostMapping("/approve-user/{action}")
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
            doctorService.save(new Doctors(user));
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

    @GetMapping("/add-user")
    public String showAddUserForm(Model model) {
        User user = new User();
        Doctors doctor = new Doctors(user);
        Patients patient = new Patients(user);
        user.setDoctorDetail(doctor);
        user.setPatientDetail(patient);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAllRoles());
        model.addAttribute("roleDisplay", roleService.getRoleDisplayNames());
        model.addAttribute("departments", deparmentService.findAll());
        return "admin/manageAccount/add_new_user";
    }
    @PostMapping("/add-user")
    public String addUser (@ModelAttribute User user,
                           RedirectAttributes redirectAttributes) {
        try {
            userService.createUserWithDetail(user, user.getAvatarFile());
            redirectAttributes.addFlashAttribute("success", "Thêm người dùng thành công!");
            return "redirect:/manage-account/list-user";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm người dùng");
            return "redirect:/add-user";
        }
    }
    @GetMapping("/update-user/{id}")
    public String showUpdateUserForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("roleDisplay", roleService.getRoleDisplayNames());
        model.addAttribute("user", userService.prepareUserForUpdate(id, model));
        model.addAttribute("departments", deparmentService.findAll());
        return "admin/manageAccount/edit_user";
    }

    @PostMapping("/update-user")
    public String showUpdateUserForm(@ModelAttribute User user, RedirectAttributes redirectAttributes) throws IOException {
        userService.createUserWithDetail(user, user.getAvatarFile());
        redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin người dùng thành công!");
        return "redirect:/manage-account/list-user";
    }
    @GetMapping("/view-user/{id}")
    public String showViewUserForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("roleDisplay", roleService.getRoleDisplayNames());
        model.addAttribute("user", userService.prepareUserForUpdate(id, model));
        model.addAttribute("departments", deparmentService.findAll());
        return "admin/manageAccount/view_user";
    }
    @PostMapping("/toggle-status/{id}")
    public String toggleUserStatus(@PathVariable Long id,RedirectAttributes redirectAttributes) {
        User user = userService.getUserById(id).get();
        if (user != null) {
            if ("active".equals(user.getStatus())) {
                user.setStatus("inactive");
                redirectAttributes.addFlashAttribute("success", "Khoá tài khoản người dùng thành công!");
            } else {
                user.setStatus("active");
                redirectAttributes.addFlashAttribute("success", "Kích hoạt tài khoản người dùng thành công!");
            }
            userService.saveUser(user);
        }
        return "redirect:/manage-account/list-user";
    }

}
