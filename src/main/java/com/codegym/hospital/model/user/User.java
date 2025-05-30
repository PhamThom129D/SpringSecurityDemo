package com.codegym.hospital.model.user;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String fullname;
    private String phonenumber;
    private String email;
    private String password;
    private String gender;
    @Transient
    private Doctors doctorDetail;

    @Transient
    private Patients patientDetail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;



    private String status;
    //    nullable -> Không được null
    //     updatable -> Không cho phép update sau khi đã insert
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Khi save lần đầu -> gán gtri cả 2
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    //    Khi save cập nhật -> chỉ cập nhật updatedAt
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public User() {
    }

    public User(Long id, String fullname, String phonenumber, String email, String password, String gender, Role role, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.fullname = fullname;
        this.phonenumber = phonenumber;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Doctors getDoctorDetail() {
        return doctorDetail;
    }

    public void setDoctorDetail(Doctors doctorDetail) {
        this.doctorDetail = doctorDetail;
    }

    public Patients getPatientDetail() {
        return patientDetail;
    }

    public void setPatientDetail(Patients patientDetail) {
        this.patientDetail = patientDetail;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "createdAt=" + createdAt +
                ", id=" + id +
                ", fullname='" + fullname + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

