-- Emergency Help Contact Website Database Schema
-- MySQL Database Schema

CREATE DATABASE IF NOT EXISTS emergency_help;
USE emergency_help;

-- Users Table
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone_number VARCHAR(15),
    date_of_birth DATE,
    blood_group VARCHAR(5),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Emergency Contacts Table
CREATE TABLE emergency_contacts (
    contact_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    contact_name VARCHAR(100) NOT NULL,
    contact_type ENUM('Parent', 'Guardian', 'Family', 'Friend', 'Doctor', 'Other') NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    email VARCHAR(100),
    relationship VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id)
);

-- Hospitals Table
CREATE TABLE hospitals (
    hospital_id INT PRIMARY KEY AUTO_INCREMENT,
    hospital_name VARCHAR(150) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100),
    postal_code VARCHAR(10),
    country VARCHAR(100) DEFAULT 'India',
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    emergency_number VARCHAR(15),
    website VARCHAR(255),
    email VARCHAR(100),
    operating_hours VARCHAR(50),
    beds_total INT,
    beds_available INT,
    has_ambulance BOOLEAN DEFAULT TRUE,
    has_icu BOOLEAN DEFAULT FALSE,
    has_trauma_center BOOLEAN DEFAULT FALSE,
    blood_bank_available BOOLEAN DEFAULT FALSE,
    services TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_city (city),
    INDEX idx_location (latitude, longitude)
);

-- Police Stations Table
CREATE TABLE police_stations (
    station_id INT PRIMARY KEY AUTO_INCREMENT,
    station_name VARCHAR(150) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100),
    postal_code VARCHAR(10),
    country VARCHAR(100) DEFAULT 'India',
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    emergency_number VARCHAR(15),
    email VARCHAR(100),
    operating_hours VARCHAR(50),
    jurisdiction_area VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_city (city),
    INDEX idx_location (latitude, longitude)
);

-- Fire Stations Table
CREATE TABLE fire_stations (
    station_id INT PRIMARY KEY AUTO_INCREMENT,
    station_name VARCHAR(150) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100),
    postal_code VARCHAR(10),
    country VARCHAR(100) DEFAULT 'India',
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    emergency_number VARCHAR(15),
    email VARCHAR(100),
    operating_hours VARCHAR(50),
    equipment_available TEXT,
    personnel_count INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_city (city),
    INDEX idx_location (latitude, longitude)
);

-- Pharmacies Table
CREATE TABLE pharmacies (
    pharmacy_id INT PRIMARY KEY AUTO_INCREMENT,
    pharmacy_name VARCHAR(150) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100),
    postal_code VARCHAR(10),
    country VARCHAR(100) DEFAULT 'India',
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    email VARCHAR(100),
    opening_time TIME,
    closing_time TIME,
    is_24_hours BOOLEAN DEFAULT FALSE,
    has_delivery BOOLEAN DEFAULT FALSE,
    services TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_city (city),
    INDEX idx_location (latitude, longitude)
);

-- Blood Banks Table
CREATE TABLE blood_banks (
    bank_id INT PRIMARY KEY AUTO_INCREMENT,
    bank_name VARCHAR(150) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100),
    postal_code VARCHAR(10),
    country VARCHAR(100) DEFAULT 'India',
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    email VARCHAR(100),
    operating_hours VARCHAR(50),
    contact_person VARCHAR(100),
    services TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_city (city),
    INDEX idx_location (latitude, longitude)
);

-- Blood Stock Table
CREATE TABLE blood_stock (
    stock_id INT PRIMARY KEY AUTO_INCREMENT,
    bank_id INT NOT NULL,
    blood_type VARCHAR(5) NOT NULL,
    units_available INT DEFAULT 0,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (bank_id) REFERENCES blood_banks(bank_id) ON DELETE CASCADE,
    UNIQUE KEY unique_bank_blood_type (bank_id, blood_type),
    INDEX idx_bank_id (bank_id)
);

-- User Saved Locations Table
CREATE TABLE user_saved_locations (
    saved_location_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    location_name VARCHAR(100) NOT NULL,
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    location_type ENUM('Home', 'Work', 'School', 'Other') NOT NULL,
    address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id)
);

-- Search History Table (Optional - for analytics)
CREATE TABLE search_history (
    search_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    search_type ENUM('Hospital', 'Police', 'Fire', 'Pharmacy', 'BloodBank') NOT NULL,
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    results_count INT,
    searched_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL,
    INDEX idx_user_id (user_id),
    INDEX idx_searched_at (searched_at)
);

-- Create indexes for better query performance
CREATE INDEX idx_hospitals_name ON hospitals(hospital_name);
CREATE INDEX idx_police_stations_name ON police_stations(station_name);
CREATE INDEX idx_fire_stations_name ON fire_stations(station_name);
CREATE INDEX idx_pharmacies_name ON pharmacies(pharmacy_name);
CREATE INDEX idx_blood_banks_name ON blood_banks(bank_name);

-- Create spatial indexes (if supported by your MySQL version)
-- ALTER TABLE hospitals ADD SPATIAL INDEX spatial_idx_hospitals (location);
-- ALTER TABLE police_stations ADD SPATIAL INDEX spatial_idx_police (location);
-- ALTER TABLE fire_stations ADD SPATIAL INDEX spatial_idx_fire (location);
-- ALTER TABLE pharmacies ADD SPATIAL INDEX spatial_idx_pharmacies (location);
-- ALTER TABLE blood_banks ADD SPATIAL INDEX spatial_idx_blood_banks (location);
