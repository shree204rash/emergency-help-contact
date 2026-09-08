# Database Setup Guide

## Overview

This directory contains the database schema and initialization scripts for the Emergency Help Contact website.

## Files

- `schema.sql` - Database schema with all tables and relationships
- `init-data.sql` - Sample data for testing and development

## Database Structure

### Core Tables

1. **users** - User account information
   - Stores user profile, authentication, and medical information
   - Primary key: user_id

2. **emergency_contacts** - User's emergency contacts
   - Linked to users table
   - Stores contact name, type, phone, and relationship

3. **hospitals** - Hospital information
   - Contains location (latitude/longitude), services, contact details
   - Has indexes for city and location-based queries
   - Tracks availability (beds, services)

4. **police_stations** - Police station information
   - Location-based service center
   - Contains jurisdiction information

5. **fire_stations** - Fire station information
   - Location-based emergency service
   - Tracks equipment and personnel

6. **pharmacies** - Pharmacy locations and services
   - Tracks operating hours and delivery options
   - Includes consultation services

7. **blood_banks** - Blood bank information
   - Tracks blood stock by type
   - Contains service information

8. **blood_stock** - Real-time blood availability
   - Linked to blood_banks
   - Tracks units available by blood type

9. **user_saved_locations** - User's frequently used locations
   - Stores home, work, school addresses
   - Linked to users table

10. **search_history** - Analytics data (optional)
    - Tracks user searches for analytics
    - Helps improve service recommendations

## Setup Instructions

### Prerequisites
- MySQL 5.7 or higher (8.0 recommended)
- MySQL CLI or GUI tool (MySQL Workbench, DBeaver, etc.)
- Database user with CREATE/ALTER privileges

### Step 1: Create Database and Schema

```bash
# Connect to MySQL
mysql -u root -p

# Execute schema
source database/schema.sql;

# Or using command line
mysql -u root -p < database/schema.sql
```

### Step 2: Initialize Sample Data

```bash
# Execute initialization script
mysql -u root -p emergency_help < database/init-data.sql

# Or from MySQL prompt
USE emergency_help;
source database/init-data.sql;
```

### Step 3: Verify Installation

```sql
-- Check if database exists
SHOW DATABASES;

-- Switch to database
USE emergency_help;

-- List all tables
SHOW TABLES;

-- Check table structure
DESC hospitals;

-- Verify sample data
SELECT COUNT(*) FROM hospitals;
SELECT COUNT(*) FROM police_stations;
```

## Data Types and Constraints

### Location Coordinates
- **Latitude**: DECIMAL(10, 8) - Range: -90 to +90
- **Longitude**: DECIMAL(11, 8) - Range: -180 to +180
- Allows accurate geographic positioning for location-based queries

### Timestamps
- All tables include `created_at` and `updated_at` timestamps
- Automatically managed by MySQL

### Indexes
- **Search Indexes**: On names (hospitals, police, fire, pharmacy names)
- **Location Indexes**: On latitude/longitude for geo-queries
- **Foreign Key Indexes**: Automatically created for referential integrity

## Common Queries

### Find Nearby Hospitals
```sql
SELECT hospital_name, address, phone_number, 
       SQRT(POW(latitude - 12.9716, 2) + POW(longitude - 77.5946, 2)) AS distance
FROM hospitals
WHERE city = 'Bangalore'
ORDER BY distance
LIMIT 10;
```

### Get Blood Bank Availability
```sql
SELECT bb.bank_name, bb.phone_number, bs.blood_type, bs.units_available
FROM blood_banks bb
JOIN blood_stock bs ON bb.bank_id = bs.bank_id
WHERE bb.city = 'Bangalore'
AND bs.blood_type = 'O+'
ORDER BY bs.units_available DESC;
```

### User's Emergency Contacts
```sql
SELECT contact_name, contact_type, phone_number, relationship
FROM emergency_contacts
WHERE user_id = 1
ORDER BY contact_type;
```

## Backup and Restore

### Backup Database
```bash
mysqldump -u root -p emergency_help > emergency_help_backup.sql
```

### Restore Database
```bash
mysql -u root -p emergency_help < emergency_help_backup.sql
```

## Performance Optimization

1. **Spatial Indexes** - For faster geographic queries
   ```sql
   ALTER TABLE hospitals ADD SPATIAL INDEX spatial_idx (location);
   ```

2. **Query Analysis**
   ```sql
   EXPLAIN SELECT * FROM hospitals WHERE city = 'Bangalore';
   ```

3. **Statistics Update**
   ```sql
   ANALYZE TABLE hospitals;
   ANALYZE TABLE police_stations;
   ```

## Maintenance

### Regular Tasks
- Monitor table sizes and optimize as needed
- Update blood stock regularly
- Archive old search history data
- Verify data integrity and constraints

### Connection Pooling
Configure connection pooling in your Java backend:
- Minimum connections: 5
- Maximum connections: 20
- Connection timeout: 30 seconds

## Troubleshooting

### Connection Issues
- Check MySQL service is running
- Verify credentials in application.properties
- Ensure database user has correct privileges

### Data Issues
- Verify foreign key constraints
- Check unique constraints (email, username)
- Monitor disk space availability

### Performance Issues
- Run ANALYZE TABLE on large tables
- Check slow query logs
- Optimize indexes based on query patterns
