package com.codegym.hospital.service.impl.user;

import com.codegym.hospital.model.user.Doctors;
import com.codegym.hospital.model.user.Patients;
import com.codegym.hospital.model.user.Role;
import com.codegym.hospital.model.user.User;
import com.codegym.hospital.repository.user.IRoleRepository;
import com.codegym.hospital.repository.user.IUserRepository;
import com.codegym.hospital.service.IDoctorService;
import com.codegym.hospital.service.IPatientService;
import com.codegym.hospital.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private BCryptPasswordEncoder passEncoder;
    @Autowired
    private IDoctorService doctorService;

    @Override
    public String registerUser(User user) {
        user.setPassword(passEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName(user.getRole().getName());
        user.setRole(role);
        String message;
        switch (role.getName()){
            case "PATIENT":
                user.setStatus("active");
                userRepository.save(user);
                patientService.save(new Patients(user));
                message = "Đăng ký thành công. Vui lòng đăng nhập lại!";
                break;
            case "DOCTOR" :
                case "RECEPTIONIST":
                    user.setStatus("pending");
                    userRepository.save(user);
                    message = "Đăng ký thành công. Chờ duyệt!";
                    break;
            default:
                user.setStatus("inactive");
                return null;
        }
        return message;
    }
    @Override
    public User loginUser(User user) {
        User userFromDb = null;

        if (user.getPhonenumber() != null && !user.getPhonenumber().isEmpty()) {
            userFromDb = isPhoneNumberExist(user.getPhonenumber());
        }
        if (userFromDb == null && user.getEmail() != null && !user.getEmail().isEmpty()) {
            userFromDb = isEmailExist(user.getEmail());
        }

        if (userFromDb != null && passEncoder.matches(user.getPassword(), userFromDb.getPassword())) {
            return userFromDb;
        }

        return null;
    }

    @Override
    public User isPhoneNumberExist(String phoneNumber)  {
        return userRepository.findByPhonenumber(phoneNumber);
    }

    @Override
    public User isEmailExist(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public List<User> getUserByStatus(String status) {
        return userRepository.findAllByStatus(status, Sort.by(Sort.Direction.DESC, "createdAt"));
    }


    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }


    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Value("${upload_file}")
    private String uploadPath;

    public String uploadFile(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            File dest = new File(uploadPath, originalFileName);
            if (dest.exists()) {
                return originalFileName;
            }

            file.transferTo(dest);
            return originalFileName;
        }
        return null;
    }

    @Override
    public void createUserWithDetail(User user, MultipartFile avatarFile) throws IOException {
        User existingUser = (user.getId() != null) ? userRepository.findById(user.getId()).orElse(null) : null;

        if (avatarFile != null && !avatarFile.isEmpty()) {
            String fileName = uploadFile(avatarFile);
            user.setAvtPath(fileName);
        } else if (existingUser != null) {
            user.setAvtPath(existingUser.getAvtPath());
        } else {
            user.setAvtPath("avt_default.png");
        }

        if (user.getId() == null) {
            user.setPassword(passEncoder.encode(user.getPassword()));
        } else if (existingUser != null) {
            user.setPassword(existingUser.getPassword());
        }

        saveUser(user);

        if (user.getRole().getId() == 3) {
            Doctors detail = user.getDoctorDetail();
            if (detail != null) {
                detail.setUser(user);

                if (user.getId() != null) {
                    Doctors existingDetail = doctorService.findByUserID(user.getId());
                    if (existingDetail != null) {
                        detail.setId(existingDetail.getId());
                    }
                }

                doctorService.save(detail);
            }

        } else if (user.getRole().getId() == 4) {
            Patients detail = user.getPatientDetail();
            if (detail != null) {
                detail.setUser(user);

                if (user.getId() != null) {
                    Patients existingDetail = patientService.getPatientByUserId(user.getId());
                    if (existingDetail != null) {
                        detail.setId(existingDetail.getId());
                    }
                }

                patientService.save(detail);
            }
        }
    }



    @Override
    public User prepareUserForUpdate(Long id, Model model) {
        Optional<User> optionalUser = getUserById(id);
        if (optionalUser.isEmpty()) {
            model.addAttribute("error", "Không tìm thấy người dùng!");
            return null;
        }

        User user = optionalUser.get();

        Doctors doctor = doctorService.findByUserID(user.getId());
        if (doctor != null) {
            user.setDoctorDetail(doctor);
        }

        Patients patient = patientService.getPatientByUserId(user.getId());
        if (patient != null) {
            user.setPatientDetail(patient);
        }
        return user;
    }

}

