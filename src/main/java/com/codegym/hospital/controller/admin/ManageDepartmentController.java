package com.codegym.hospital.controller.admin;

import com.codegym.hospital.model.deparment.Departments;
import com.codegym.hospital.model.user.User;
import com.codegym.hospital.service.*;
import com.codegym.hospital.service.impl.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manageDepartment")
public class ManageDepartmentController {
    @Autowired
    private IDeparmentService deparmentService;
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/listDepartments")
    public String listDepartments(Model model) {
        model.addAttribute("departments",deparmentService.findAll());
        return "admin/manageDepartments/list_departments";
    }
    @GetMapping("/addDepartment")
    public String showAddDeparmentForm(Model model) {
        if (!model.containsAttribute("department")) {
            model.addAttribute("department", new Departments());
        }
        model.addAttribute("departments", deparmentService.findAll());
        return "admin/manageDepartments/add_department";
    }

    @PostMapping("/addDepartment")
    public String save(@ModelAttribute Departments department, RedirectAttributes redirectAttributes) {
        if (deparmentService.existsByName(department.getName())) {
            redirectAttributes.addFlashAttribute("error", "Tên khoa khám đã tồn tại!");
            redirectAttributes.addFlashAttribute("department",department);
            return "redirect:/manageDepartment/addDepartment";
        }
        departmentService.save(department);
        return "redirect:/manageDepartment/listDepartments";
    }

    @PostMapping("/editDepartment")
    public String update(@ModelAttribute Departments department, RedirectAttributes redirectAttributes) {
        if (deparmentService.existsByName(department.getName())) {
            redirectAttributes.addFlashAttribute("error", "Tên khoa khám đã tồn tại!");
            return "redirect:/manageDepartment/updateDepartment/" + department.getId();
        }
        departmentService.save(department);
        return "redirect:/manageDepartment/listDepartments";
    }

    @GetMapping("/updateDepartment/{id}")
    public String showAddDeparmentForm(Model model, @PathVariable Long id) {
        model.addAttribute("departments",deparmentService.findAll());
        model.addAttribute("department",deparmentService.findById(id));
        return "admin/manageDepartments/edit_department";
    }
    @PostMapping("/toggleStatus/{id}")
    public String toggleUserStatus(@PathVariable Long id,RedirectAttributes redirectAttributes) {
        Departments department = deparmentService.findById(id);
        if (department != null) {
            if ("active".equals(department.getStatus())) {
                department.setStatus("inactive");
                redirectAttributes.addFlashAttribute("success", "Tạm dừng hoạt động khoa khám thành công!");
            } else {
                department.setStatus("active");
                redirectAttributes.addFlashAttribute("success", "Khoa khám đã được mở lại!");
            }
            deparmentService.save(department);
        }
        return "redirect: /manageDepartment/listDepartments";
    }
}
