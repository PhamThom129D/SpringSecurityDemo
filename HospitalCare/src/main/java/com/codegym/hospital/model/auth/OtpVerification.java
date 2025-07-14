package com.codegym.hospital.model.auth;

import com.codegym.hospital.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
public class OtpVerification {

    private Long userId;
    private String otpCode;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean isVerified = false;

    public OtpVerification(LocalDateTime createdAt, LocalDateTime expiresAt, boolean isVerified, String otpCode, Long userId) {
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.isVerified = isVerified;
        this.otpCode = otpCode;
        this.userId = userId;
    }

    public OtpVerification() {
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "OtpVerification{" +
                "createdAt=" + createdAt +
                ", userId=" + userId +
                ", otpCode='" + otpCode + '\'' +
                ", expiresAt=" + expiresAt +
                ", isVerified=" + isVerified +
                '}';
    }
}