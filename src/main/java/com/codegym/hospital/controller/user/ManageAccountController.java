package com.codegym.hospital.controller.user;

import com.codegym.hospital.model.deparment.Departments;
import com.codegym.hospital.model.user.Doctors;
import com.codegym.hospital.model.user.Patients;
import com.codegym.hospital.model.user.User;
import com.codegym.hospital.service.IDeparmentService;
import com.codegym.hospital.service.IEmailService;
import com.codegym.hospital.service.IRoleService;
import com.codegym.hospital.service.IUserService;
import com.codegym.hospital.service.impl.user.DoctorService;
import com.codegym.hospital.service.impl.user.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
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
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IDeparmentService deparmentService;


    @GetMapping("listUser")
    public String showListUser(Model model) {
        List<User> userList = new ArrayList<>();
        userList.addAll(userService.getUserByStatus("inactive"));
        userList.addAll(userService.getUserByStatus("active"));
        model.addAttribute("roleDisplay", roleService.getRoleDisplayNames());
        model.addAttribute("users", userList);
        return "admin/manageAccount/list_users";
    }
    @GetMapping("approveUser")
    public String test(Model model) {
        model.addAttribute("users", userService.getUserByStatus("pending"));
        return "admin/manageAccount/approve_users";
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

    @GetMapping("addUser")
    public String showAddUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAllRoles());
        model.addAttribute("roleDisplay", roleService.getRoleDisplayNames());
        model.addAttribute("departments", deparmentService.findAll());
        return "admin/manageAccount/add_new_user";
    }
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user,
                          @RequestParam Map<String, String> params,
                          @RequestParam(value = "avatarDoctorFile", required = false) MultipartFile avatarDoctorFile,
                          @RequestParam(value = "avatarPatientFile", required = false) MultipartFile avatarPatientFile,
                          RedirectAttributes redirectAttributes , HttpSession session) {
        try {
            User user1 = (User) session.getAttribute("loggedInUser");
            System.out.println(user1);
            MultipartFile avatarFile = null;
            if (user.getRole().getId() == 3) {
                avatarFile = avatarDoctorFile;
            } else if (user.getRole().getId() == 4) {
                avatarFile = avatarPatientFile;
            }

            userService.createUserWithDetail(user, params, avatarFile);

            redirectAttributes.addFlashAttribute("success", "Thêm người dùng thành công!");
            return "redirect:/manageAccount/listUser";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm người dùng");
            return "redirect:/addUser";
        }
    }




    @GetMapping("updateUser/{id}")
    public String showUpdateUserForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (!optionalUser.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại");
            return "redirect:/users";
        }
        User user = optionalUser.get();
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAllRoles());
        model.addAttribute("roleDisplay", roleService.getRoleDisplayNames());
        System.out.println(user);
        return "admin/manageAccount/edit_user";
    }

}

