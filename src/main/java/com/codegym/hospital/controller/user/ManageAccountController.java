package com.codegym.hospital.controller.user;


import com.codegym.hospital.model.deparment.Departments;
import com.codegym.hospital.model.user.Doctors;
import com.codegym.hospital.model.user.Patients;
import com.codegym.hospital.model.user.User;
import com.codegym.hospital.service.*;
import com.codegym.hospital.service.impl.user.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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
    @Autowired
    private IDoctorService doctorService;
    @Autowired
    private IPatientService patientService;


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

    @GetMapping("addUser")
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
    @PostMapping("/addUser")
    public String addUser (@ModelAttribute User user,
                           RedirectAttributes redirectAttributes) {
        try {
            userService.createUserWithDetail(user, user.getAvatarFile());
            redirectAttributes.addFlashAttribute("success", "Thêm người dùng thành công!");
            return "redirect:/manageAccount/listUser ";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm người dùng");
            return "redirect:/addUser ";
        }
    }
    @GetMapping("updateUser/{id}")
    public String showUpdateUserForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<User> optionalUser = userService.getUserById(id);
        User user = optionalUser.get();
        Doctors doctor = doctorService.findByUserID(user.getId());
//        Departments department = deparmentService.findById(doctor.getDepartment().getId());
//        doctor.setDepartment(department);
        model.addAttribute("departments", deparmentService.findAll());
        Patients patient = patientService.getPatientByUserId(user.getId());
        user.setDoctorDetail(doctor);
        user.setPatientDetail(patient);
        if (!optionalUser.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại");
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        model.addAttribute("roleDisplay", roleService.getRoleDisplayNames());
        System.out.println(user);
        return "admin/manageAccount/edit_user";
    }

}

