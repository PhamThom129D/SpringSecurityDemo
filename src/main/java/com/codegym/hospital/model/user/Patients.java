package com.codegym.hospital.model.user;


import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
public class Patients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @Column(name = "date_of_birth", nullable = true)
    private LocalDate dateOfBirth;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "avt_path", length = 10000)
    private String avtPath;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status = true;

    @Transient
    private MultipartFile avatarFile;

    public Patients() {
    }

    public Patients(User user) {
        this.user = user;
    }

    public Patients(Integer id, User user, LocalDate dateOfBirth, String address, String avtPath, Boolean status) {
        this.id = id;
        this.user = user;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.avtPath = avtPath;
        this.status = status;
    }

    public Patients(Integer id, User user, LocalDate dateOfBirth, String address, MultipartFile avatarFile, Boolean status) {
        this.id = id;
        this.user = user;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.avatarFile = avatarFile;
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MultipartFile getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(MultipartFile avatarFile) {
        this.avatarFile = avatarFile;
    }

    public String getAvtPath() {
        return avtPath;
    }

    public void setAvtPath(String avtPath) {
        this.avtPath = avtPath;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Patients{" +
                "address='" + address + '\'' +
                ", id=" + id +
                ", user=" + user +
                ", dateOfBirth=" + dateOfBirth +
                ", avtPath='" + avtPath + '\'' +
                ", status=" + status +
                ", avatarFile=" + avatarFile +
                '}';
    }
}
