Insert into Roles(name) value 
("ADMIN"),("RECEPTIONIST"),("DOCTOR"),("PATIENT");

INSERT INTO departments (name, description) VALUES
('Khoa Tai Mũi Họng', 'Khám và điều trị các bệnh về tai, mũi, họng; thường dùng siêu âm, chụp X-quang.'),
('Khoa Răng Hàm Mặt', 'Điều trị các bệnh về răng, hàm, mặt; thường dùng siêu âm, chụp X-quang.'),
('Khoa Mắt', 'Khám và điều trị các bệnh về mắt; có thể dùng siêu âm chuyên sâu.'),
('Khoa Cấp Cứu', 'Tiếp nhận các trường hợp cấp cứu, phối hợp với chụp X-quang, siêu âm để chẩn đoán nhanh.'),
('Khoa Nội Tổng Quát', 'Điều trị các bệnh nội khoa; sử dụng đa dạng xét nghiệm hình ảnh như siêu âm, X-quang, CT scan.');


INSERT INTO users (fullname, phonenumber, password, email, gender, role_id, status, created_at)
VALUES 
('Nguyen Van A', '0900000001', '$2a$10$ABCDEFGH1ABCDEFGH1ABCDEFGH1ABCDEFGH1ABCDEFGH1AB', 'a1@example.com', 'MALE', 3, 'pending',NOW(6)),
('Tran Thi B', '0900000002', '$2a$10$ABCDEFGH2ABCDEFGH2ABCDEFGH2ABCDEFGH2ABCDEFGH2AB', 'b2@example.com', 'FEMALE', 3, 'pending', NOW(6)),
('Le Van C', '0900000003', '$2a$10$ABCDEFGH3ABCDEFGH3ABCDEFGH3ABCDEFGH3ABCDEFGH3AB', 'c3@example.com', 'MALE', 3, 'pending', NOW(6)),
('Pham Thi D', '0900000004', '$2a$10$ABCDEFGH4ABCDEFGH4ABCDEFGH4ABCDEFGH4ABCDEFGH4AB', 'd4@example.com', 'FEMALE', 3, 'pending', NOW(6)),
('Hoang Van E', '0900000005', '$2a$10$ABCDEFGH5ABCDEFGH5ABCDEFGH5ABCDEFGH5ABCDEFGH5AB', 'e5@example.com', 'MALE', 3, 'pending', NOW(6)),
('Dang Thi F', '0900000006', '$2a$10$ABCDEFGH6ABCDEFGH6ABCDEFGH6ABCDEFGH6ABCDEFGH6AB', 'f6@example.com', 'FEMALE', 3, 'pending', NOW(6)),
('Nguyen Van G', '0900000007', '$2a$10$ABCDEFGH7ABCDEFGH7ABCDEFGH7ABCDEFGH7ABCDEFGH7AB', 'g7@example.com', 'MALE', 3, 'pending', NOW(6)),
('Tran Thi H', '0900000008', '$2a$10$ABCDEFGH8ABCDEFGH8ABCDEFGH8ABCDEFGH8ABCDEFGH8AB', 'h8@example.com', 'FEMALE', 3, 'pending', NOW(6)),
('Le Van I', '0900000009', '$2a$10$ABCDEFGH9ABCDEFGH9ABCDEFGH9ABCDEFGH9ABCDEFGH9AB', 'i9@example.com', 'MALE', 3, 'pending', NOW(6)),
('Pham Thi J', '0900000010', '$2a$10$ABCDEFGH0ABCDEFGH0ABCDEFGH0ABCDEFGH0ABCDEFGH0AB', 'j10@example.com', 'FEMALE', 3, 'pending', NOW(6)),
('Nguyen Thi K', '0900000011', '$2a$10$AA1AA1AA1AA1AA1AA1AA1uY5dM1', 'k11@example.com', 'FEMALE', 3, 'pending', NOW(6)),
('Tran Van L',   '0900000012', '$2a$10$BB2BB2BB2BB2BB2BB2BB2vY5dM2', 'l12@example.com', 'MALE',   3, 'pending', NOW(6)),
('Le Thi M',     '0900000013', '$2a$10$CC3CC3CC3CC3CC3CC3CC3wY5dM3', 'm13@example.com', 'FEMALE', 3, 'pending', NOW(6)),
('Pham Van N',   '0900000014', '$2a$10$DD4DD4DD4DD4DD4DD4DD4xY5dM4', 'n14@example.com', 'MALE',   3, 'pending', NOW(6)),
('Hoang Thi O',  '0900000015', '$2a$10$EE5EE5EE5EE5EE5EE5EE5yY5dM5', 'o15@example.com', 'FEMALE', 3, 'pending', NOW(6)),
('Dang Van P',   '0900000016', '$2a$10$FF6FF6FF6FF6FF6FF6FF6zY5dM6', 'p16@example.com', 'MALE',   3, 'pending', NOW(6)),
('Nguyen Thi Q', '0900000017', '$2a$10$GG7GG7GG7GG7GG7GG7GG7aY5dM7', 'q17@example.com', 'FEMALE', 3, 'pending', NOW(6)),
('Tran Van R',   '0900000018', '$2a$10$HH8HH8HH8HH8HH8HH8HH8bY5dM8', 'r18@example.com', 'MALE',   3, 'pending', NOW(6)),
('Le Thi S',     '0900000019', '$2a$10$II9II9II9II9II9II9II9cY5dM9', 's19@example.com', 'FEMALE', 3, 'pending', NOW(6)),
('Pham Van T',   '0900000020', '$2a$10$JJ0JJ0JJ0JJ0JJ0JJ0JJ0dY5dM0', 't20@example.com', 'MALE',   3, 'pending', NOW(6)),
('Hoang Thi U',  '0900000021', '$2a$10$KK1KK1KK1KK1KK1KK1KK1eY5dM1', 'u21@example.com', 'FEMALE', 3, 'pending', NOW(6)),
('Dang Van V',   '0900000022', '$2a$10$LL2LL2LL2LL2LL2LL2LL2fY5dM2', 'v22@example.com', 'MALE',   3, 'pending', NOW(6)),
('Nguyen Thi W', '0900000023', '$2a$10$MM3MM3MM3MM3MM3MM3MM3gY5dM3', 'w23@example.com', 'FEMALE', 3, 'pending', NOW(6)),
('Tran Van X',   '0900000024', '$2a$10$NN4NN4NN4NN4NN4NN4NN4hY5dM4', 'x24@example.com', 'MALE',   3, 'pending', NOW(6)),
('Le Thi Y',     '0900000025', '$2a$10$OO5OO5OO5OO5OO5OO5OO5iY5dM5', 'y25@example.com', 'FEMALE', 3, 'pending', NOW(6)),
('Pham Van Z',   '0900000026', '$2a$10$PP6PP6PP6PP6PP6PP6PP6jY5dM6', 'z26@example.com', 'MALE',   3, 'pending', NOW(6)),
('Hoang Thi A1', '0900000027', '$2a$10$QQ7QQ7QQ7QQ7QQ7QQ7QQ7kY5dM7', 'a127@example.com', 'FEMALE', 3, 'pending', NOW(6)),
('Dang Van B1',  '0900000028', '$2a$10$RR8RR8RR8RR8RR8RR8RR8lY5dM8', 'b128@example.com', 'MALE',   3, 'pending', NOW(6)),
('Nguyen Thi C1','0900000029', '$2a$10$SS9SS9SS9SS9SS9SS9SS9mY5dM9', 'c129@example.com', 'FEMALE', 3, 'pending', NOW(6)),
('Tran Van D1',  '0900000030', '$2a$10$TT0TT0TT0TT0TT0TT0TT0nY5dM0', 'd130@example.com', 'MALE',   3, 'pending', NOW(6));
