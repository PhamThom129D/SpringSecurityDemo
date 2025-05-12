create database hospital;
use hospital ;
CREATE TABLE roles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE
);
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fullname VARCHAR(100) NOT NULL,
    phonenumber VARCHAR(15) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE,
    role_id INT NOT NULL,
    status BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_users_role_id FOREIGN KEY (role_id) REFERENCES roles(id)
);
CREATE TABLE otpVerifications (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    otp_code VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    is_verified BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_otp_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE departments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);
CREATE TABLE doctors (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL UNIQUE,
    degree VARCHAR(100),
    bio TEXT,
    department_id INT NOT NULL,
    status BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_doctor_user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_doctor_department_id FOREIGN KEY (department_id) REFERENCES departments(id)
);
CREATE TABLE navigationPoints (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    location_description TEXT,
    x_coordinate DECIMAL(10, 6),
    y_coordinate DECIMAL(10, 6),
    point_type VARCHAR(50),
    status BOOLEAN DEFAULT TRUE
);
CREATE TABLE navigationEdges (
    id INT PRIMARY KEY AUTO_INCREMENT,
    start_point_id INT NOT NULL,
    end_point_id INT NOT NULL,
    distance DECIMAL(10, 2),
    description TEXT,
    status BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_edge_start_point FOREIGN KEY (start_point_id) REFERENCES navigationPoints(id),
    CONSTRAINT fk_edge_end_point FOREIGN KEY (end_point_id) REFERENCES navigationPoints(id)
);
CREATE TABLE clinicRooms (
    id INT PRIMARY KEY AUTO_INCREMENT,
    department_id INT NOT NULL,
    room_number VARCHAR(20) NOT NULL UNIQUE,
point_id INT,
    status BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_clinic_room_department_id FOREIGN KEY (department_id) REFERENCES departments(id),
    CONSTRAINT fk_clinic_room_navigation_point_id FOREIGN KEY (point_id) REFERENCES navigationPoints(id)
);
CREATE TABLE doctors_clinicRooms (
    doctor_id INT NOT NULL,
    room_id INT NOT NULL,
    role_in_room VARCHAR(50),
    status BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_doctor_clinic_room_doctor_id FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_doctor_clinic_room_room_id FOREIGN KEY (room_id) REFERENCES clinicRooms(id)
);
CREATE TABLE schedules (
    id INT PRIMARY KEY AUTO_INCREMENT,
    doctor_id INT NOT NULL,
    room_id INT NOT NULL,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    is_present BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_schedule_doctor_id FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_schedule_room_id FOREIGN KEY (room_id) REFERENCES clinicRooms(id)
);
CREATE TABLE insurances (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    provider VARCHAR(100),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status BOOLEAN DEFAULT TRUE
);
CREATE TABLE patients (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    date_of_birth DATE NOT NULL,
    gender VARCHAR(10),
    address TEXT,
    status BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_patient_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE patientInsurances (
    id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    insurance_id INT NOT NULL,
    insurance_code VARCHAR(50),
    issue_date DATE,
    expiry_date DATE,
    status BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_patient_insurance_patient_id FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_patient_insurance_insurance_id FOREIGN KEY (insurance_id) REFERENCES insurances(id)
);
CREATE TABLE feeTypes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fee_name VARCHAR(100) NOT NULL,
    base_price DECIMAL(10, 2) NOT NULL,
    unit VARCHAR(50),
    description TEXT,
    status BOOLEAN DEFAULT TRUE
);
CREATE TABLE insuranceCoverages (
    id INT PRIMARY KEY AUTO_INCREMENT,
    insurance_id INT NOT NULL,
    fee_type_id INT NOT NULL,
    coverage_percentage DECIMAL(5, 2) NOT NULL,
    max_coverage_percentage DECIMAL(5, 2),
    CONSTRAINT fk_insurance_coverage_insurance_id FOREIGN KEY (insurance_id) REFERENCES insurances(id),
    CONSTRAINT fk_insurance_coverage_fee_type_id FOREIGN KEY (fee_type_id) REFERENCES feeTypes(id)
);
CREATE TABLE appointments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    department_id INT NOT NULL,
    room_id INT NOT NULL,
    reason TEXT,
    schedule_date DATE NOT NULL,
    schedule_time TIME NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    note TEXT,
    status VARCHAR(50) DEFAULT 'Scheduled', -- Trạng thái mặc định là 'Scheduled'
    CONSTRAINT fk_appointment_patient_id FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_appointment_department_id FOREIGN KEY (department_id) REFERENCES departments(id),
    CONSTRAINT fk_appointment_room_id FOREIGN KEY (room_id) REFERENCES clinicRooms(id)
);
CREATE TABLE waiting_lists (
    id INT PRIMARY KEY AUTO_INCREMENT,
    room_id INT NOT NULL,
    appointment_id INT NOT NULL,
    queue_number INT NOT NULL,
    status VARCHAR(50) DEFAULT 'Waiting',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    note TEXT,
    CONSTRAINT fk_waiting_list_room_id FOREIGN KEY (room_id) REFERENCES clinicRooms(id),
    CONSTRAINT fk_waiting_list_appointment_id FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);
CREATE TABLE medicalRecords (
    id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_id INT NOT NULL,
    diagnosis TEXT,
    start_date DATE NOT NULL,
    end_date DATE,
    status VARCHAR(50) DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    note TEXT,
    CONSTRAINT fk_medical_record_patient_id FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_medical_record_doctor_id FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_medical_record_appointment_id FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);
CREATE TABLE medications (
    id INT PRIMARY KEY AUTO_INCREMENT,
    medication_name VARCHAR(255) NOT NULL,
    dosage_form VARCHAR(100),
    strength VARCHAR(100),
    manufacturer VARCHAR(255),
    description TEXT
);
CREATE TABLE prescriptions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    record_id INT NOT NULL,
    medication_id INT NOT NULL,
    duration VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_prescription_record_id FOREIGN KEY (record_id) REFERENCES medicalRecords(id),
    CONSTRAINT fk_prescription_medication_id FOREIGN KEY (medication_id) REFERENCES medications(id)
);
CREATE TABLE prescriptions_mediations (
    prescription_id INT NOT NULL,
    medication_id INT NOT NULL,
    quantity INT NOT NULL,
    frequency VARCHAR(100) NOT NULL,
    CONSTRAINT fk_prescription_mediation_prescription_id FOREIGN KEY (prescription_id) REFERENCES prescriptions(id),
    CONSTRAINT fk_prescription_mediation_medication_id FOREIGN KEY (medication_id) REFERENCES medications(id)
);
CREATE TABLE appointmentFees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    appointment_id INT NOT NULL,
    fee_type_id INT NOT NULL,
    quantity INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) DEFAULT 'Pending',
    CONSTRAINT fk_appointment_fee_appointment_id FOREIGN KEY (appointment_id) REFERENCES appointments(id),
    CONSTRAINT fk_appointment_fee_fee_type_id FOREIGN KEY (fee_type_id) REFERENCES feeTypes(id)
);
CREATE TABLE paymentMethods (
    id INT PRIMARY KEY AUTO_INCREMENT,
    payment_method_name VARCHAR(100) NOT NULL
);
CREATE TABLE invoices (
    id INT PRIMARY KEY AUTO_INCREMENT,
    appointment_id INT NOT NULL,
    payment_method_id INT NOT NULL,
    discount_total DECIMAL(10, 2) DEFAULT 0,
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) DEFAULT 'Pending',
    paid_at TIMESTAMP,
    CONSTRAINT fk_invoice_appointment_id FOREIGN KEY (appointment_id) REFERENCES appointments(id),
    CONSTRAINT fk_invoice_payment_method_id FOREIGN KEY (payment_method_id) REFERENCES paymentMethods(id)
);
CREATE TABLE serviceOrders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    appointment_id INT NOT NULL,
    fee_type_id INT NOT NULL,
    doctor_id INT NOT NULL,
    invoice_id INT NOT NULL,
    quantity INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'Pending',
    note TEXT,
    CONSTRAINT fk_service_order_appointment_id FOREIGN KEY (appointment_id) REFERENCES appointments(id),
    CONSTRAINT fk_service_order_fee_type_id FOREIGN KEY (fee_type_id) REFERENCES feeTypes(id),
    CONSTRAINT fk_service_order_doctor_id FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_service_order_invoice_id FOREIGN KEY (invoice_id) REFERENCES invoices(id)
);
CREATE TABLE notificationTypes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(100) NOT NULL
);
CREATE TABLE notifications (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    notification_type_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    status VARCHAR(50) DEFAULT 'Unsent',
    sent_at TIMESTAMP,
    read_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_notification_user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_notification_type_id FOREIGN KEY (notification_type_id) REFERENCES notificationTypes(id)
);
