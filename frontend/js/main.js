// Main Application Logic

// Global state
let currentUser = null;
let currentLocation = null;
let searchResults = [];

// Initialize app on page load
document.addEventListener('DOMContentLoaded', function () {
    initializeApp();
});

function initializeApp() {
    console.log('Initializing Emergency Help Application...');

    // Initialize map
    mapService.initializeMap('map', 20, 78);

    // Set up event listeners
    setupEventListeners();

    // Check if user is already logged in
    checkUserSession();

    // Show notification
    showNotification('Welcome to Emergency Help!', 'info');
}

// Setup all event listeners
function setupEventListeners() {
    // "I Need Help" Button
    document.getElementById('needHelpBtn').addEventListener('click', function () {
        showNotification('Locating nearest emergency services...', 'info');
        requestLocationAndSearchAll();
    });

    // Get Current Location Button
    document.getElementById('getCurrentLocationBtn').addEventListener('click', function () {
        getCurrentLocationHandler();
    });

    // Search Button
    document.getElementById('searchBtn').addEventListener('click', function () {
        handleSearch();
    });

    // Login/Signup Buttons
    document.getElementById('loginBtn').addEventListener('click', function () {
        openLoginModal();
    });

    document.getElementById('signupBtn').addEventListener('click', function () {
        openSignupModal();
    });

    // Login Form
    document.getElementById('loginForm').addEventListener('submit', function (e) {
        e.preventDefault();
        handleLogin();
    });

    // Signup Form
    document.getElementById('signupForm').addEventListener('submit', function (e) {
        e.preventDefault();
        handleSignup();
    });

    // Close modals when clicking outside
    window.addEventListener('click', function (event) {
        const loginModal = document.getElementById('loginModal');
        const signupModal = document.getElementById('signupModal');
        if (event.target === loginModal) {
            closeLoginModal();
        }
        if (event.target === signupModal) {
            closeSignupModal();
        }
    });
}

// Get current location handler
async function getCurrentLocationHandler() {
    const statusDiv = document.getElementById('locationStatus');
    statusDiv.innerHTML = '<span class="loading"></span> Getting your location...';

    try {
        const position = await geolocationService.getCurrentPosition();
        currentLocation = position;
        statusDiv.innerHTML = `<span style="color: #27ae60;"><i class="fas fa-check-circle"></i> Location found: ${position.latitude.toFixed(4)}, ${position.longitude.toFixed(4)}</span>`;
        
        // Update map with user location
        mapService.setUserLocation(position.latitude, position.longitude);
        
        showNotification('Location detected! Use filters to search for services.', 'success');
    } catch (error) {
        statusDiv.innerHTML = `<span style="color: #e74c3c;"><i class="fas fa-exclamation-circle"></i> ${error.message}</span>`;
        showNotification(error.message, 'error');
    }
}

// Request location and search all nearby services
async function requestLocationAndSearchAll() {
    try {
        const position = await geolocationService.getCurrentPosition();
        currentLocation = position;
        
        // Update map
        mapService.setUserLocation(position.latitude, position.longitude);
        
        // Search for all services
        const radius = 5; // Default 5km
        const allServices = [];

        try {
            const hospitals = await apiService.getNearbyHospitals(position.latitude, position.longitude, radius);
            const police = await apiService.getNearbyPoliceStations(position.latitude, position.longitude, radius);
            const fire = await apiService.getNearbyFireStations(position.latitude, position.longitude, radius);
            const pharmacies = await apiService.getNearbyPharmacies(position.latitude, position.longitude, radius);
            const bloodBanks = await apiService.getNearbyBloodBanks(position.latitude, position.longitude, radius);

            // Add type to each service for identification
            hospitals.forEach(h => h.type = 'Hospital');
            police.forEach(p => p.type = 'Police Station');
            fire.forEach(f => f.type = 'Fire Station');
            pharmacies.forEach(ph => ph.type = 'Pharmacy');
            bloodBanks.forEach(b => b.type = 'Blood Bank');

            allServices.push(...hospitals, ...police, ...fire, ...pharmacies, ...bloodBanks);

            searchResults = allServices;
            displayResults(allServices);
        } catch (apiError) {
            console.error('API Error:', apiError);
            // Use sample data if API fails
            displaySampleResults(position);
        }
    } catch (error) {
        showNotification(error.message, 'error');
    }
}

// Handle search with filters
async function handleSearch() {
    if (!currentLocation) {
        showNotification('Please enable location first!', 'warning');
        return;
    }

    const serviceType = document.getElementById('serviceTypeFilter').value;
    const radius = parseFloat(document.getElementById('radiusFilter').value) || 5;

    showNotification('Searching nearby services...', 'info');

    try {
        let results = [];

        if (!serviceType || serviceType === 'hospitals') {
            const hospitals = await apiService.getNearbyHospitals(
                currentLocation.latitude,
                currentLocation.longitude,
                radius
            );
            hospitals.forEach(h => h.type = 'Hospital');
            results.push(...hospitals);
        }

        if (!serviceType || serviceType === 'police') {
            const police = await apiService.getNearbyPoliceStations(
                currentLocation.latitude,
                currentLocation.longitude,
                radius
            );
            police.forEach(p => p.type = 'Police Station');
            results.push(...police);
        }

        if (!serviceType || serviceType === 'fire') {
            const fire = await apiService.getNearbyFireStations(
                currentLocation.latitude,
                currentLocation.longitude,
                radius
            );
            fire.forEach(f => f.type = 'Fire Station');
            results.push(...fire);
        }

        if (!serviceType || serviceType === 'pharmacy') {
            const pharmacies = await apiService.getNearbyPharmacies(
                currentLocation.latitude,
                currentLocation.longitude,
                radius
            );
            pharmacies.forEach(ph => ph.type = 'Pharmacy');
            results.push(...pharmacies);
        }

        if (!serviceType || serviceType === 'blood') {
            const bloodBanks = await apiService.getNearbyBloodBanks(
                currentLocation.latitude,
                currentLocation.longitude,
                radius
            );
            bloodBanks.forEach(b => b.type = 'Blood Bank');
            results.push(...bloodBanks);
        }

        searchResults = results;
        displayResults(results);
    } catch (error) {
        console.error('Search error:', error);
        showNotification('Error searching services. Showing sample data.', 'warning');
        displaySampleResults(currentLocation);
    }
}

// Display results
function displayResults(results) {
    if (results.length === 0) {
        showNotification('No services found in the specified radius.', 'warning');
        return;
    }

    const resultsSection = document.getElementById('results');
    const resultsList = document.getElementById('resultsList');

    // Clear previous results
    resultsList.innerHTML = '';

    // Group results by type
    const grouped = groupBy(results, 'type');

    // Display results
    Object.keys(grouped).forEach(type => {
        const services = grouped[type];
        const typeGroup = document.createElement('div');
        typeGroup.className = 'results-group';
        typeGroup.innerHTML = `<h3 style="margin-bottom: 1rem; color: #667eea;">${type}s (${services.length})</h3>`;

        services.forEach(service => {
            const distance = geolocationService.calculateDistance(
                currentLocation.latitude,
                currentLocation.longitude,
                service.latitude || service.lat,
                service.longitude || service.lon
            );

            const card = createResultCard(service, distance, type);
            typeGroup.appendChild(card);
        });

        resultsList.appendChild(typeGroup);
    });

    // Show results section
    resultsSection.style.display = 'block';
    resultsSection.scrollIntoView({ behavior: 'smooth' });

    // Update map
    const mainService = results[0].type.toLowerCase().includes('hospital') ? 'hospitals' :
        results[0].type.toLowerCase().includes('police') ? 'police' :
        results[0].type.toLowerCase().includes('fire') ? 'fire' :
        results[0].type.toLowerCase().includes('pharmacy') ? 'pharmacy' : 'blood';
    
    mapService.addServiceMarkers(results, mainService);

    showNotification(`Found ${results.length} services nearby!`, 'success');
}

// Create result card HTML
function createResultCard(service, distance, type) {
    const card = document.createElement('div');
    card.className = 'result-card';
    card.innerHTML = `
        <h3>${service.hospitalName || service.stationName || service.pharmacyName || service.bankName || 'Service'}</h3>
        <p><i class="fas fa-map-marker-alt"></i> ${service.address}</p>
        <p><i class="fas fa-phone"></i> ${service.phoneNumber || service.phone || 'N/A'}</p>
        <p><span class="distance"><i class="fas fa-road"></i> ${distance.toFixed(2)} km away</span></p>
        ${service.operatingHours ? `<p><i class="fas fa-clock"></i> ${service.operatingHours}</p>` : ''}
        ${service.services ? `<p><i class="fas fa-briefcase-medical"></i> ${service.services}</p>` : ''}
        <button class="btn-service" onclick="viewDetails('${service.hospitalId || service.stationId || service.pharmacyId || service.bankId}')">View Details</button>
    `;
    return card;
}

// Display sample results (when API is not available)
function displaySampleResults(position) {
    const sampleData = [
        {
            type: 'Hospital',
            hospitalName: 'Apollo Hospitals',
            address: '123 MG Road, Bangalore',
            phoneNumber: '9008006000',
            latitude: 12.9716,
            longitude: 77.5946,
            services: 'Emergency Care, ICU, Trauma Center'
        },
        {
            type: 'Police Station',
            stationName: 'Indiranagar Police Station',
            address: '456 Indiranagar Main Road, Bangalore',
            phoneNumber: '080-25618555',
            latitude: 12.9716,
            longitude: 77.6412,
        },
        {
            type: 'Fire Station',
            stationName: 'Bangalore Fire Station - East',
            address: '234 East Street, Bangalore',
            phoneNumber: '080-23591234',
            latitude: 12.9716,
            longitude: 77.6412,
            services: 'Fire Truck, Ladder Unit, Rescue Equipment'
        },
        {
            type: 'Pharmacy',
            pharmacyName: 'Apollo Pharmacy',
            address: '123 Main Street, Bangalore',
            phoneNumber: '080-25671234',
            latitude: 12.9716,
            longitude: 77.5946,
            services: 'Prescription Filling, Medicine Delivery'
        },
        {
            type: 'Blood Bank',
            bankName: 'Blood Bank of India - Bangalore',
            address: '234 Vidhana Soudha Road, Bangalore',
            phoneNumber: '080-22252525',
            latitude: 12.9798,
            longitude: 77.5906,
            services: 'Blood Collection, Transfusion, Testing'
        }
    ];

    displayResults(sampleData);
}

// Navigate to service category
function navigateToService(service) {
    const section = document.getElementById('find-help');
    document.getElementById('serviceTypeFilter').value = service;
    section.scrollIntoView({ behavior: 'smooth' });
    showNotification(`Searching for ${service}...`, 'info');
}

// View service details
function viewDetails(serviceId) {
    showNotification(`Loading details for service ${serviceId}...`, 'info');
    // This could open a detailed view or modal with more information
}

// Close results section
function closeResults() {
    document.getElementById('results').style.display = 'none';
    mapService.clearMarkers();
}

// Login handler
async function handleLogin() {
    const form = document.getElementById('loginForm');
    const email = form.querySelector('input[type="email"]').value;
    const password = form.querySelector('input[type="password"]').value;

    showNotification('Logging in...', 'info');

    try {
        const response = await apiService.loginUser(email, password);
        if (response && response.token) {
            currentUser = response.user;
            localStorage.setItem('authToken', response.token);
            localStorage.setItem('userId', response.user.userId);
            
            updateUIForLoggedInUser();
            closeLoginModal();
            form.reset();
            showNotification('Login successful!', 'success');
        } else {
            showNotification('Login failed. Please check your credentials.', 'error');
        }
    } catch (error) {
        showNotification('Login error: ' + error.message, 'error');
    }
}

// Signup handler
async function handleSignup() {
    const form = document.getElementById('signupForm');
    const inputs = form.querySelectorAll('input');
    const [name, email, phone, password, confirmPassword] = Array.from(inputs).map(i => i.value);

    if (password !== confirmPassword) {
        showNotification('Passwords do not match!', 'error');
        return;
    }

    showNotification('Creating account...', 'info');

    try {
        const response = await apiService.registerUser({
            firstName: name.split(' ')[0],
            lastName: name.split(' ')[1] || '',
            email: email,
            phoneNumber: phone,
            password: password,
        });

        if (response && response.userId) {
            currentUser = response;
            localStorage.setItem('userId', response.userId);
            updateUIForLoggedInUser();
            closeSignupModal();
            form.reset();
            showNotification('Account created successfully!', 'success');
        } else {
            showNotification('Signup failed. Please try again.', 'error');
        }
    } catch (error) {
        showNotification('Signup error: ' + error.message, 'error');
    }
}

// Check user session
function checkUserSession() {
    const token = localStorage.getItem('authToken');
    const userId = localStorage.getItem('userId');

    if (token && userId) {
        // User is logged in
        currentUser = { userId: userId };
        updateUIForLoggedInUser();
    }
}

// Update UI for logged in user
function updateUIForLoggedInUser() {
    const authButtons = document.querySelector('.auth-buttons');
    authButtons.innerHTML = `
        <button class="btn-login" onclick="logout()" style="background-color: #e74c3c; color: #fff; border: none;">
            <i class="fas fa-sign-out-alt"></i> Logout
        </button>
        <button class="btn-signup" onclick="openProfileModal()">
            <i class="fas fa-user"></i> Profile
        </button>
    `;
}

// Logout
function logout() {
    localStorage.removeItem('authToken');
    localStorage.removeItem('userId');
    currentUser = null;
    location.reload();
}

// Open profile modal (placeholder)
function openProfileModal() {
    showNotification('Profile feature coming soon!', 'info');
}

// Modal functions
function openLoginModal() {
    document.getElementById('loginModal').style.display = 'block';
}

function closeLoginModal() {
    document.getElementById('loginModal').style.display = 'none';
}

function openSignupModal() {
    document.getElementById('signupModal').style.display = 'block';
    closeLoginModal();
}

function closeSignupModal() {
    document.getElementById('signupModal').style.display = 'none';
}

// Show notification
function showNotification(message, type = 'info') {
    const notification = document.getElementById('notification');
    notification.textContent = message;
    notification.className = `notification ${type}`;
    notification.style.opacity = '1';

    setTimeout(() => {
        notification.style.opacity = '0';
    }, 4000);
}

// Utility function to group array by property
function groupBy(arr, key) {
    return arr.reduce((result, obj) => {
        const group = obj[key];
        if (!result[group]) {
            result[group] = [];
        }
        result[group].push(obj);
        return result;
    }, {});
}

// Smooth scroll navigation
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            target.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        }
    });
});
