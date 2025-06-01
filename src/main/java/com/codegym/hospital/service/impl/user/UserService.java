package com.codegym.hospital.service.impl.user;

import com.codegym.hospital.model.deparment.Departments;
import com.codegym.hospital.model.user.Doctors;
import com.codegym.hospital.model.user.Patients;
import com.codegym.hospital.model.user.Role;
import com.codegym.hospital.model.user.User;
import com.codegym.hospital.repository.user.IRoleRepository;
import com.codegym.hospital.repository.user.IUserRepository;
import com.codegym.hospital.service.IDeparmentService;
import com.codegym.hospital.service.IDoctorService;
import com.codegym.hospital.service.IPatientService;
import com.codegym.hospital.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
    private IDeparmentService deparmentService;
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
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String fileName = uploadFile(avatarFile);
            user.setAvtPath(fileName);
        }else {
            user.setAvtPath("avt_default.png");
        }
        saveUser (user);
        if (user.getRole().getId() == 3) {
            Doctors doctor = user.getDoctorDetail();
            doctor.setUser(user);
            user.setDoctorDetail(doctor);
            doctorService.save(doctor);
        } else if (user.getRole().getId() == 4) {
            Patients patient = user.getPatientDetail();
            patient.setUser(user);
            user.setPatientDetail(patient);
            patientService.save(patient);
        }
    }

}
