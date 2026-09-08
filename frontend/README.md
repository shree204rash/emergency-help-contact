# Frontend Setup Guide

## Overview

The frontend is a responsive web application built with HTML5, CSS3, and vanilla JavaScript. It provides a user-friendly interface for finding emergency services based on location.

## Technology Stack

- **HTML5** - Structure and semantic markup
- **CSS3** - Styling with flexbox and grid
- **JavaScript (ES6+)** - Interactivity and functionality
- **Leaflet.js** - Interactive mapping
- **Font Awesome** - Icons
- **OpenStreetMap** - Map tiles

## File Structure

```
frontend/
├── index.html                 # Main HTML file
├── css/
│   ├── style.css             # Main styling
│   └── responsive.css        # Mobile/responsive styles
├── js/
│   ├── main.js               # Main application logic
│   ├── api.js                # API service layer
│   ├── geolocation.js        # Geolocation service
│   └── map.js                # Map service (Leaflet)
└── assets/
    └── images/               # Image assets
```

## Features Implemented

### Core Features
1. **Home Page**
   - Hero section with "I Need Help" button
   - Service categories overview
   - Emergency contacts quick reference

2. **Service Discovery**
   - Browse by service type (Hospital, Police, Fire, Pharmacy, Blood Bank)
   - Location-based search
   - Distance calculation
   - Service details display

3. **Location Services**
   - Geolocation API integration
   - Real-time location tracking
   - Distance calculation (Haversine formula)
   - Map visualization with Leaflet

4. **User Authentication**
   - Login modal
   - Registration modal
   - Session management
   - Local storage for auth tokens

5. **Emergency Contacts**
   - Quick access to emergency numbers
   - Save favorite contacts (backend integration)
   - Emergency contact management

6. **Responsive Design**
   - Mobile-first approach
   - Tablet optimization
   - Desktop experience
   - Touch-friendly controls

## API Integration

### Available Services

The frontend communicates with the backend API at `http://localhost:8080/api`

**Endpoints Used:**
- `/hospitals` - Get/search hospitals
- `/police-stations` - Get/search police stations
- `/fire-stations` - Get/search fire stations
- `/pharmacies` - Get/search pharmacies
- `/blood-banks` - Get/search blood banks
- `/users/login` - User login
- `/users/register` - User registration
- `/users/{id}` - User profile

## How to Run

### Local Development

1. **Simple HTTP Server**
   ```bash
   cd frontend
   python -m http.server 8000
   # or
   python3 -m http.server 8000
   ```

2. **Using Node.js (http-server)**
   ```bash
   npm install -g http-server
   http-server frontend -p 8000
   ```

3. **Using Live Server (VS Code)**
   - Install "Live Server" extension
   - Right-click index.html → "Open with Live Server"

4. **Access the application**
   - Open browser and go to `http://localhost:8000`
   - Or `http://localhost:3000` depending on your server

### Configuration

To change the API endpoint, edit `frontend/js/api.js`:

```javascript
const API_BASE_URL = 'http://localhost:8080/api';
// Change to your backend server URL
```

## Key JavaScript Functions

### Geolocation
- `geolocationService.getCurrentPosition()` - Get current location
- `geolocationService.calculateDistance()` - Calculate distance between points
- `geolocationService.watchPosition()` - Watch for location changes

### Map
- `mapService.initializeMap()` - Initialize Leaflet map
- `mapService.setUserLocation()` - Set user marker on map
- `mapService.addServiceMarkers()` - Add service location markers
- `mapService.clearMarkers()` - Remove all markers

### API
- `apiService.getNearbyHospitals()` - Search nearby hospitals
- `apiService.loginUser()` - User login
- `apiService.registerUser()` - User registration
- Similar methods for other services

### UI Functions
- `handleSearch()` - Process search with filters
- `displayResults()` - Show search results
- `showNotification()` - Display notification toast
- `openLoginModal()` / `closeLoginModal()` - Auth modals

## Browser Compatibility

- Chrome 90+
- Firefox 88+
- Safari 14+
- Edge 90+
- Mobile browsers (iOS Safari, Chrome Mobile)

## Required Features

For full functionality, ensure:
1. **HTTPS or localhost** - Geolocation requires secure context
2. **Backend API running** - At `http://localhost:8080/api`
3**CORS enabled** - Backend must allow frontend requests
4. **Database populated** - With sample emergency services

## Troubleshooting

### Geolocation Not Working
- Check if browser has geolocation permission
- Ensure using HTTPS or localhost
- Check browser console for errors
- Verify browser supports Geolocation API

### Map Not Loading
- Check internet connection (needs to load map tiles)
- Verify Leaflet.js library is loaded
- Check console for JavaScript errors
- Verify map container has proper height

### API Not Responding
- Ensure backend server is running
- Check backend is on `http://localhost:8080`
- Verify CORS is configured
- Check network tab in DevTools for failed requests

### Styling Issues
- Clear browser cache (Ctrl+Shift+Delete)
- Hard refresh (Ctrl+Shift+R)
- Check that CSS files are loading
- Verify font-awesome CDN is accessible

## Performance Optimization

1. **Caching**
   - Service worker for offline support (optional)
   - LocalStorage for user data

2. **Lazy Loading**
   - Load scripts after DOM ready
   - Defer non-critical resources

3. **Mobile Optimization**
   - Minimize bundle size
   - Optimize images
   - Reduce API calls

## Future Enhancements

- [ ] Offline mode with service worker
- [ ] Push notifications for emergencies
- [ ] Dark mode toggle
- [ ] Multi-language support
- [ ] User favorites/bookmarks
- [ ] Real-time traffic integration
- [ ] Emergency contact QR code
- [ ] Progressive Web App (PWA) features

## Security Notes

1. **Authentication**
   - Never store sensitive data in localStorage
   - Use HTTP-only cookies for tokens (when backend supports)
   - Implement token refresh mechanism

2. **API Calls**
   - Validate all user inputs
   - Use CORS properly
   - Sanitize data from API

3. **Geolocation**
   - Only request when necessary
   - Respect user privacy
   - Show clear permission prompts

## Testing

### Manual Testing
1. Test location access with permissions
2. Search for different service types
3. Test responsive design at various screen sizes
4. Test authentication flow
5. Verify map interactions

### Browser DevTools
- Test geolocation simulation
- Test network throttling
- Test offline mode
- Check console for errors

## Support

For issues or questions:
1. Check browser console for errors
2. Verify backend API is running
3. Test with sample data (app provides fallback)
4. Check network requests in DevTools

---

**Ready to serve users in emergencies!** 🚑🚔🚒
