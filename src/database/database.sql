CREATE DATABASE IF NOT EXISTS healthfirst_pims;


-- ============================================
-- HEALTHFIRST PHARMACY INVENTORY SYSTEM
-- DATABASE SCRIPT
-- ============================================

-- Select database

USE healthfirst_pims;

-- ============================================
-- TABLE 1: USERS
-- ============================================

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('Admin', 'Cashier') NOT NULL,
    full_name VARCHAR(100) NOT NULL
);


-- ============================================
-- TABLE 2: SUPPLIERS
-- ============================================

CREATE TABLE suppliers (
    supplier_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    contact_person VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT
);


-- ============================================
-- TABLE 3: MEDICINES
-- ============================================

CREATE TABLE medicines (
    medicine_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    company VARCHAR(100) NOT NULL,
    medicine_type VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity_in_stock INT NOT NULL,
    reorder_level INT NOT NULL,
    expiry_date DATE NOT NULL,
    supplier_id INT,
    
    CONSTRAINT fk_medicine_supplier
        FOREIGN KEY (supplier_id)
        REFERENCES suppliers(supplier_id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);


-- ============================================
-- TABLE 4: SALES
-- ============================================

CREATE TABLE sales (
    sale_id INT AUTO_INCREMENT PRIMARY KEY,
    sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL,
    user_id INT NOT NULL,

    CONSTRAINT fk_sale_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);


-- ============================================
-- TABLE 5: SALE ITEMS
-- ============================================

CREATE TABLE sale_items (
    sale_item_id INT AUTO_INCREMENT PRIMARY KEY,
    sale_id INT NOT NULL,
    medicine_id INT NOT NULL,
    quantity_sold INT NOT NULL,
    price_at_sale DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_sale_item_sale
        FOREIGN KEY (sale_id)
        REFERENCES sales(sale_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT fk_sale_item_medicine
        FOREIGN KEY (medicine_id)
        REFERENCES medicines(medicine_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);


-- ============================================
-- SAMPLE ADMIN USER
-- ============================================

INSERT INTO users
(username, password, role, full_name)
VALUES
('admin', 'admin123', 'Admin', 'System Administrator');


-- ============================================
-- SAMPLE CASHIER USER
-- ============================================

INSERT INTO users
(username, password, role, full_name)
VALUES
('cashier', 'cash123', 'Cashier', 'HealthFirst Cashier');


-- ============================================
-- SAMPLE SUPPLIERS
-- ============================================

INSERT INTO suppliers
(name, contact_person, phone, email, address)
VALUES
('PharmaPlus Distributors', 'John Smith', '0111234567',
 'info@pharmaplus.com', 'Johannesburg, South Africa'),

('MedCare Suppliers', 'Sarah Johnson', '0119876543',
 'info@medcare.com', 'Pretoria, South Africa'),

('HealthMed Distributors', 'Michael Brown', '0125557890',
 'info@healthmed.com', 'Midrand, South Africa');


-- ============================================
-- SAMPLE MEDICINES
-- ============================================

INSERT INTO medicines
(name, company, medicine_type, price, quantity_in_stock,
 reorder_level, expiry_date, supplier_id)
VALUES
('Panado', 'Adcock Ingram', 'Tablet', 35.50, 100, 20,
 '2027-12-31', 1),

('Amoxil', 'GSK', 'Capsule', 85.00, 50, 10,
 '2027-08-30', 2),

('Benylin', 'Johnson & Johnson', 'Syrup', 65.75, 30, 10,
 '2027-05-15', 3),

('Voltaren', 'Novartis', 'Cream', 95.00, 15, 5,
 '2027-03-20', 1),

('Disprin', 'Reckitt', 'Tablet', 28.50, 80, 15,
 '2028-01-10', 2);

