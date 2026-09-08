-- Initial Sample Data for Emergency Help Contact Website
-- This file contains sample data for testing purposes

USE emergency_help;

-- Sample Users
INSERT INTO users (username, email, password_hash, first_name, last_name, phone_number, blood_group) VALUES
('john_doe', 'john@example.com', 'hash_password_123', 'John', 'Doe', '9876543210', 'O+'),
('jane_smith', 'jane@example.com', 'hash_password_456', 'Jane', 'Smith', '9876543211', 'A+'),
('raj_kumar', 'raj@example.com', 'hash_password_789', 'Raj', 'Kumar', '9876543212', 'B+');

-- Sample Emergency Contacts
INSERT INTO emergency_contacts (user_id, contact_name, contact_type, phone_number, relationship) VALUES
(1, 'Sarah Doe', 'Family', '9876543220', 'Sister'),
(1, 'Dr. Michael Johnson', 'Doctor', '9876543221', 'Family Doctor'),
(2, 'Robert Smith', 'Family', '9876543222', 'Husband'),
(3, 'Priya Kumar', 'Family', '9876543223', 'Mother');

-- Sample Hospitals (Major cities in India)
INSERT INTO hospitals (hospital_name, address, city, state, postal_code, latitude, longitude, phone_number, emergency_number, website, has_ambulance, has_icu, has_trauma_center, blood_bank_available, services) VALUES
('Apollo Hospitals', '123 MG Road, Next to Shopping Mall', 'Bangalore', 'Karnataka', '560001', 12.9716, 77.5946, '9008006000', '080-40611111', 'www.apollohospitals.com', TRUE, TRUE, TRUE, TRUE, 'Emergency Care, ICU, Trauma Center, Blood Bank, Surgery'),
('Fortis Healthcare', '456 Brigade Road', 'Bangalore', 'Karnataka', '560025', 12.9352, 77.6245, '9008008080', '080-40611222', 'www.fortishealthcare.com', TRUE, TRUE, FALSE, TRUE, 'Emergency Care, ICU, Cardiology, Orthopedics'),
('Max Super Specialty', '789 Inner Ring Road', 'New Delhi', 'Delhi', '110016', 28.5355, 77.2707, '9876543330', '011-46044444', 'www.maxhealthcare.com', TRUE, TRUE, TRUE, TRUE, 'Emergency Care, ICU, Trauma Center, Neurology'),
('Lilavati Hospital', '321 Lilavati Lane', 'Mumbai', 'Maharashtra', '400050', 19.0176, 72.8194, '9876543331', '022-66529888', 'www.lilavati.co.in', TRUE, TRUE, TRUE, TRUE, 'Emergency Care, ICU, Cardiac Care, Blood Bank');

-- Sample Police Stations
INSERT INTO police_stations (station_name, address, city, state, postal_code, latitude, longitude, phone_number, emergency_number, jurisdiction_area) VALUES
('Indiranagar Police Station', '456 Indiranagar Main Road', 'Bangalore', 'Karnataka', '560038', 12.9716, 77.6412, '080-25618555', '100 / 9845098450', 'Indiranagar, Whitefield Area'),
('Koramangala Police Station', '789 Koramangala Lane', 'Bangalore', 'Karnataka', '560034', 12.9353, 77.6245, '080-41287676', '100 / 9845098451', 'Koramangala, Madivala Area'),
('Central Police Station', '123 New Delhi Road', 'New Delhi', 'Delhi', '110016', 28.5355, 77.2707, '011-23941041', '100 / 9840098450', 'Central Delhi Zone'),
('Colaba Police Station', '321 Colaba Lane', 'Mumbai', 'Maharashtra', '400005', 18.9597, 72.8245, '022-66950400', '100 / 9845098452', 'Colaba, Fort Area');

-- Sample Fire Stations
INSERT INTO fire_stations (station_name, address, city, state, postal_code, latitude, longitude, phone_number, emergency_number, equipment_available) VALUES
('Bangalore Fire Station - East', '234 East Street', 'Bangalore', 'Karnataka', '560001', 12.9716, 77.6412, '080-23591234', '101 / 9845098460', 'Fire Truck, Ladder Unit, Rescue Equipment, Water Tanker'),
('Bangalore Fire Station - West', '567 West Avenue', 'Bangalore', 'Karnataka', '560034', 12.9353, 77.5900, '080-23592345', '101 / 9845098461', 'Fire Truck, Ambulance, Rescue Boat'),
('New Delhi Fire Station', '890 Fire Lane', 'New Delhi', 'Delhi', '110016', 28.5355, 77.2707, '011-23451234', '101 / 9845098462', 'Advanced Fire Truck, Aerial Ladder, Hazmat Equipment'),
('Mumbai Fire Brigade', '456 Fire Street', 'Mumbai', 'Maharashtra', '400050', 19.0176, 72.8194, '022-23847777', '101 / 9845098463', 'Modern Fire Truck, High-rise Ladder, Rescue Boat');

-- Sample Pharmacies
INSERT INTO pharmacies (pharmacy_name, address, city, state, postal_code, latitude, longitude, phone_number, opening_time, closing_time, is_24_hours, has_delivery, services) VALUES
('Apollo Pharmacy', '123 Main Street', 'Bangalore', 'Karnataka', '560001', 12.9716, 77.5946, '080-25671234', '08:00:00', '22:00:00', FALSE, TRUE, 'Prescription Filling, Medicine Delivery, Health Consultation'),
('MediPlus Pharmacy', '456 Brigade Road', 'Bangalore', 'Karnataka', '560025', 12.9352, 77.6245, '080-41234567', '06:00:00', '23:59:59', TRUE, TRUE, 'Emergency Medicine, Consultation, Delivery'),
('Generic Pharmacy', '789 Connaught Place', 'New Delhi', 'Delhi', '110001', 28.6305, 77.1869, '011-41234567', '07:00:00', '23:00:00', FALSE, TRUE, 'Prescription Filling, Consultation, Delivery'),
('Chemist World', '321 Marine Drive', 'Mumbai', 'Maharashtra', '400020', 19.0424, 72.8263, '022-61234567', '06:00:00', '23:59:59', TRUE, TRUE, 'Emergency Service, Medicine Delivery, Consultation');

-- Sample Blood Banks
INSERT INTO blood_banks (bank_name, address, city, state, postal_code, latitude, longitude, phone_number, operating_hours, contact_person, services) VALUES
('Blood Bank of India - Bangalore', '234 Vidhana Soudha Road', 'Bangalore', 'Karnataka', '560001', 12.9798, 77.5906, '080-22252525', '09:00 - 18:00', 'Dr. Rajesh Sharma', 'Blood Collection, Transfusion, Testing, Donor Registration'),
('Red Cross Blood Bank - Bangalore', '567 Cubbon Park', 'Bangalore', 'Karnataka', '560025', 12.9352, 77.5906, '080-25671515', '08:00 - 20:00', 'Ms. Priya Nair', 'Emergency Blood Supply, Donation Drive, Testing'),
('National Blood Bank - Delhi', '890 Rajendra Place', 'New Delhi', 'Delhi', '110001', 28.5912, 77.2009, '011-45671234', '09:00 - 17:00', 'Dr. Amit Verma', 'Blood Collection, Testing, Transfusion Service'),
('Mumbai Blood Bank', '456 Mahim Causeway', 'Mumbai', 'Maharashtra', '400016', 19.0408, 72.8260, '022-61234567', '08:00 - 20:00', 'Ms. Deepa Gupta', 'Emergency Blood Supply, Platelet Collection, Testing');

-- Sample Blood Stock
INSERT INTO blood_stock (bank_id, blood_type, units_available) VALUES
(1, 'O+', 45),
(1, 'O-', 12),
(1, 'A+', 38),
(1, 'A-', 8),
(1, 'B+', 42),
(1, 'B-', 5),
(1, 'AB+', 15),
(1, 'AB-', 3),
(2, 'O+', 52),
(2, 'A+', 35),
(2, 'B+', 48),
(2, 'AB+', 18),
(3, 'O+', 40),
(3, 'A+', 32),
(4, 'O+', 55),
(4, 'B+', 45);

-- Sample User Saved Locations
INSERT INTO user_saved_locations (user_id, location_name, latitude, longitude, location_type, address) VALUES
(1, 'Home', 12.9716, 77.5946, 'Home', '123 MG Road, Bangalore'),
(1, 'Work', 12.9352, 77.6245, 'Work', '456 Brigade Road, Bangalore'),
(2, 'Home', 28.5355, 77.2707, 'Home', '789 Nehru Place, New Delhi'),
(3, 'School', 19.0176, 72.8194, 'School', '321 School Lane, Mumbai');
