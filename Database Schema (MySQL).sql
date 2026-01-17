-- Insert test users into MySQL
USE clinic_db;

-- Admin User
INSERT INTO users (username, password, email, full_name, role) 
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTV8bXq', 'admin@clinic.com', 'Administrator', 'ADMIN');

-- Doctor User
INSERT INTO users (username, password, email, full_name, role) 
VALUES ('doctor', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTV8bXq', 'doctor@clinic.com', 'Dr. John Smith', 'DOCTOR');

-- Patient User
INSERT INTO users (username, password, email, full_name, role) 
VALUES ('patient', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTV8bXq', 'patient@clinic.com', 'Jane Doe', 'PATIENT');

-- Insert doctor details
INSERT INTO doctors (user_id, specialization, qualification, available_from, available_to, consultation_fee)
VALUES (2, 'Cardiologist', 'MD Cardiology', '09:00:00', '17:00:00', 2000.00);

-- Insert patient details
INSERT INTO patients (user_id, date_of_birth, age, gender, blood_group, address, phone, emergency_contact)
VALUES (3, '1990-05-15', 33, 'FEMALE', 'O+', '123 Main St, City', '0771234567', '0777654321');