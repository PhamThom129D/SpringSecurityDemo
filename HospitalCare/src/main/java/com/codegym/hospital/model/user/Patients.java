package com.codegym.hospital.model.user;


import org.springframework.format.annotation.DateTimeFormat;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;


    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status = true;

    public Patients() {
    }

    public Patients(User user) {
        this.user = user;
    }

    public Patients(Integer id, User user, LocalDate dateOfBirth, String address, Boolean status) {
        this.id = id;
        this.user = user;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.status = status;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
                ", status=" + status +
                '}';
    }
}
