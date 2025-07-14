package com.codegym.hospital.model.user;

import com.codegym.hospital.model.deparment.Departments;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;


@Entity
@Table(name = "doctors")
public class Doctors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true, referencedColumnName = "id")
    private User user;

    @Column(length = 100)
    private String degree;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = true, referencedColumnName = "id")
    private Departments department;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status = true;


    public Doctors() {
    }

    public Doctors(User user) {
        this.user = user;
    }


    public Doctors(Integer id, User user, String degree, String bio, Departments department, Boolean status) {
        this.id = id;
        this.user = user;
        this.degree = degree;
        this.bio = bio;
        this.department = department;
        this.status = status;
    }


    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Departments getDepartment() {
        return department;
    }

    public void setDepartment(Departments department) {
        this.department = department;
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
        return "Doctors{" +
                ", id=" + id +
                ", user=" + user +
                ", degree='" + degree + '\'' +
                ", bio='" + bio + '\'' +
                ", department=" + department +
                ", status=" + status +
                '}';
    }
}