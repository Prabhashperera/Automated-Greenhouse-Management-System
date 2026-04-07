# Automated Greenhouse Management System (AGMS)

A cloud-native, microservice-based platform designed to monitor and automate greenhouse environments. The system integrates with live IoT data to maintain ideal growing conditions for various crop zones.

## 🚀 Getting Started

To ensure the system starts correctly, services must be launched in a specific order to allow for configuration fetching and service discovery.

### **Prerequisites**
- **Java 17+**
- **MySQL Server** (Ensure databases `agms_zone`, `agms_automation`, and `agms_crop` are created)
- **Node.js & npm** (For the Frontend)

---

## 🛠 Infrastructure Startup (Step-by-Step)

Launch these services **first** in the following order:

1. **Config Server (Port: 8888)**
   - Manages centralized configuration for all microservices.
   - Wait for "Started ConfigServerApplication" log.

2. **Service Registry - Eureka (Port: 8761)**
   - Acts as the phonebook for all microservices.
   - Access the dashboard at: `http://localhost:8761`

3. **API Gateway (Port: 8080)**
   - The single entry point for all client requests.
   - Handles routing and CORS configuration.

---

## 🏗 Domain Microservices Startup

Once infrastructure is UP, start these services:

4. **Zone Management Service (Port: 8081)**
   - Manages greenhouse sections and temperature thresholds.
   - Registers virtual devices with the IoT Backend.

5. **Sensor Telemetry Service (Port: 8082)**
   - Fetches live data from the External IoT API every 10 seconds.
   - Pushes data to the Automation Service.

6. **Automation & Control Service (Port: 8085)**
   - The system's "Brain." Compares live telemetry against zone limits.
   - Persists automated actions (Fans/Heaters) to the database.

7. **Crop Inventory Service (Port: 8084)**
   - Tracks crop batches through their lifecycle: `SEEDLING` -> `VEGETATIVE` -> `HARVESTED`.

---

## 💻 Frontend Setup

8. **AgriSmart Dashboard (Port: 5173)**
   - Navigate to `green-house-dashboard-front-end` folder.
   - Run `npm install` then `npm run dev`.
   - View the live dashboard at `http://localhost:5173`.

---

## 🧪 API Testing
A complete **Postman Collection** is included in the root directory as `AGMS_Tests.postman_collection.json`. 
- **Base URL**: `http://localhost:8080` (All requests route through the Gateway).
