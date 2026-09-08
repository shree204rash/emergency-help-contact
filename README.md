# Emergency Help Contact Website

A location-based web application that helps users quickly find emergency services including hospitals, police stations, fire stations, pharmacies, and blood banks.

## 🎯 Features

- **Home Page**: Dashboard with quick access to emergency services
- **Emergency Services**: Browse hospitals, police stations, fire stations, pharmacies, and blood banks
- **Nearby Help**: Location-based service finder with map integration
- **Hospital Details**: View comprehensive information including address, phone, services, and directions
- **User Account**: Manage profile, save emergency contacts, and store frequent locations
- **Emergency Contacts**: Save and manage trusted contact numbers
- **I Need Help Button**: One-click access to nearby emergency services with map display

## ⚠️ Important Disclaimer

**This website is an assistance tool and does NOT replace official emergency services.**

- Always call official emergency numbers (911 in US, 100 for Police in India, 102 for Ambulance in India)
- Location results are for guidance only and may not reflect real-time availability
- In life-threatening situations, always call emergency services directly

## 🛠️ Technology Stack

| Component | Technology |
|-----------|------------|
| Frontend | HTML5, CSS3, JavaScript (ES6+) |
| Backend | Java (Spring Boot) |
| Database | MySQL |
| Location Services | Browser Geolocation API |
| Mapping | Google Maps API / Leaflet |
| Server | Apache Tomcat |

## 📁 Project Structure

```
emergency-help-contact/
├── frontend/
│   ├── index.html
│   ├── css/
│   │   ├── style.css
│   │   └── responsive.css
│   ├── js/
│   │   ├── main.js
│   │   ├── geolocation.js
│   │   ├── map.js
│   │   └── api.js
│   └── assets/
│       └── images/
├── backend/
│   ├── src/
│   │   ├── main/java/com/emergency/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── model/
│   │   │   └── config/
│   │   └── resources/
│   │       └── application.properties
│   ├── pom.xml
│   └── README.md
├── database/
│   ├── schema.sql
│   ├── init-data.sql
│   └── README.md
├── docs/
│   ├── API_DOCUMENTATION.md
│   ├── DATABASE_DESIGN.md
│   └── USER_GUIDE.md
└── README.md
```

## 🗄️ Database Tables

1. **Users** - User account information
2. **Emergency_Contacts** - User's saved emergency contacts
3. **Hospitals** - Hospital details and services
4. **Police_Stations** - Police station information
5. **Fire_Stations** - Fire station details
6. **Pharmacies** - Pharmacy locations and services
7. **Blood_Banks** - Blood bank information
8. **User_Saved_Locations** - User's frequently saved locations

## 🚀 Getting Started

### Prerequisites
- Java 11 or higher
- MySQL 8.0 or higher
- Node.js (for frontend build tools - optional)
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/shree204rash/emergency-help-contact.git
   cd emergency-help-contact
   ```

2. **Setup Database**
   ```bash
   mysql -u root -p
   CREATE DATABASE emergency_help;
   USE emergency_help;
   source database/schema.sql;
   source database/init-data.sql;
   ```

3. **Configure Backend**
   - Update `backend/src/main/resources/application.properties`
   - Set database username, password, and URL

4. **Build Backend**
   ```bash
   cd backend
   mvn clean install
   mvn spring-boot:run
   ```

5. **Access Frontend**
   - Open `frontend/index.html` in your browser
   - Or serve with a local server:
   ```bash
   cd frontend
   python -m http.server 8000
   ```

## 📡 API Endpoints

### Hospitals
- `GET /api/hospitals` - Get all hospitals
- `GET /api/hospitals/{id}` - Get hospital details
- `GET /api/hospitals/nearby?lat=&lon=&radius=` - Find nearby hospitals

### Police Stations
- `GET /api/police-stations` - Get all police stations
- `GET /api/police-stations/{id}` - Get station details
- `GET /api/police-stations/nearby?lat=&lon=&radius=` - Find nearby stations

### Fire Stations
- `GET /api/fire-stations` - Get all fire stations
- `GET /api/fire-stations/{id}` - Get station details
- `GET /api/fire-stations/nearby?lat=&lon=&radius=` - Find nearby stations

### Pharmacies
- `GET /api/pharmacies` - Get all pharmacies
- `GET /api/pharmacies/{id}` - Get pharmacy details
- `GET /api/pharmacies/nearby?lat=&lon=&radius=` - Find nearby pharmacies

### Blood Banks
- `GET /api/blood-banks` - Get all blood banks
- `GET /api/blood-banks/{id}` - Get blood bank details
- `GET /api/blood-banks/nearby?lat=&lon=&radius=` - Find nearby blood banks

### User Accounts
- `POST /api/users/register` - Register new user
- `POST /api/users/login` - Login user
- `GET /api/users/{id}` - Get user profile
- `PUT /api/users/{id}` - Update user profile

### Emergency Contacts
- `GET /api/users/{id}/emergency-contacts` - Get user's emergency contacts
- `POST /api/users/{id}/emergency-contacts` - Add emergency contact
- `DELETE /api/users/{id}/emergency-contacts/{contactId}` - Delete contact

## 👥 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## ⚠️ Emergency Numbers Reference

### India
- Police: 100
- Ambulance: 102
- Fire: 101
- Unified Emergency: 112

### United States
- Emergency: 911

### Europe
- Emergency: 112

## 📧 Support & Contact

For issues, suggestions, or contributions, please open an issue or contact the project maintainers.

---

**Remember: In case of actual emergency, always call the official emergency services directly.**
