package com.codegym.hospital.controller.user;

import com.codegym.hospital.model.deparment.Departments;
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
    private IDepartmentService departmentService;
    @Autowired
    private IDoctorService doctorService;
    @Autowired
    private IPatientService patientService;

    @GetMapping("listUser")
    public String showListUser(Model model) {
        List<User> userList = new ArrayList<>();
        userList.addAll(userService.getUserByStatus("inactive"));
        userList.addAll(userService.getUserByStatus("active"));
        model.addAttribute("users", userList);
        return "admin/manageAccount/list_users";
    }
    @GetMapping("addUser")
    public String showAddUserForm(Model model) {
        User user = new User();
        Doctors doctor = new Doctors();
        List<Departments> departments = departmentService.getAllDepartments();
        Patients patient = new Patients();
        model.addAttribute("user", user);
        model.addAttribute("doctor", doctor);
        model.addAttribute("patient", patient);
        model.addAttribute("departments", departments);
        model.addAttribute("roles", roleService.findAllRoles());
        return "admin/manageAccount/add_new_user";
    }
    @GetMapping("viewUser/{id}")
    public String showViewUserForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (!optionalUser.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại");
            return "redirect:/manageUser/listUser";
        }
        User user = optionalUser.get();
        model.addAttribute("user", user);
        return "admin/manageAccount/view_user";
    }
    @GetMapping("updateUser/{id}")
    public String showUpdateUserForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (!optionalUser.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại");
            return "redirect:/manageUser/listUser";
        }
        User user = optionalUser.get();
        Patients patient = patientService.getPatientByUserId(user.getId());
        Doctors doctor = doctorService.findByUserID(user.getId());
        List<Departments> departments = departmentService.getAllDepartments();
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAllRoles());
        if(user.getRole().getName().equals("DOCTOR")){
            model.addAttribute("departments", departments);
            model.addAttribute("doctor", doctor);
        } else if (user.getRole().getName().equals("PATIENT")) {
            model.addAttribute("patient", patient);
        }
        return "admin/manageAccount/edit_user";
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


}

