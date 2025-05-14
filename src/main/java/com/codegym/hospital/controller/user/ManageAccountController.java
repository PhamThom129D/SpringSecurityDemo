package com.codegym.hospital.controller.user;

import com.codegym.hospital.model.user.User;
import com.codegym.hospital.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/manageAccount")
public class ManageAccountController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/approveUser")
    public String showApproveUserForm(        Model model,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "createdAt") String sortBy,
                                              @RequestParam(defaultValue = "desc") String sortDir,
                                              @RequestParam(defaultValue = "") String keyword) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> pageUsers = userService.findPendingUsers(keyword, pageable);

        model.addAttribute("userPage", pageUsers);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageUsers.getTotalPages());
        model.addAttribute("totalItems", pageUsers.getTotalElements());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        return "admin/manageAccount/approve-users";
    }
}
