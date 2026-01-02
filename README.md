🌤 Weather Search Application

🔗 Demo & Screenshots
👉 https://drive.google.com/drive/folders/1jRAbjCNQwXg1govmDxQLYXZ71zuvkMGv

📖 Project Overview

A full-stack Weather Search Application that allows users to search for the current weather of any city.

The application fetches real-time weather data from the OpenWeatherMap API, implements a multi-level caching strategy for performance, and displays rich weather information using a modern, animated UI.

This project is designed to demonstrate:

Clean REST API design

Performance optimization using caching

Scalable backend architecture

Polished frontend UI & UX

✨ Key Features

🔍 Search current weather by city name

⚡ Fast responses using multi-level caching

🌡 Displays temperature, feels-like, humidity, wind speed, condition

🖼 Dynamic weather icons

⏳ Loading spinner with graceful error handling

🎨 Modern glassmorphism UI with animations

🧠 Clean and extensible REST API

💾 Database-backed cache for persistence

🏗 System Architecture
Frontend (Angular)
        |
        | HTTP Request
        v
Backend (Spring Boot REST API)
        |
        | Fetch / Cache
        v
OpenWeatherMap API

🧩 Tech Stack
🔹 Backend
Technology	Purpose
Java 21	Core backend language
Spring Boot 3	REST API framework
Spring Data JPA	Database interaction
MySQL	Persistent cache storage
Caffeine Cache	In-memory caching
OpenWeatherMap API	Weather data provider
🔹 Frontend
Technology	Purpose
Angular (Standalone Components)	UI Framework
HTML5	Markup
CSS3	Styling & animations
TypeScript	Frontend logic
🌐 External API Used

OpenWeatherMap – Current Weather API
🔗 https://openweathermap.org/current

🔁 End-to-End Application Flow
1️⃣ User Interaction

User enters a city name

Presses Enter or clicks Search

2️⃣ Frontend (Angular)

Sends request to backend:

GET /api/weather/{city}


Displays loading spinner while waiting

3️⃣ Backend Processing
Step	Action
1	Check in-memory (Caffeine) cache
2	If not found → check MySQL DB cache
3	If cache expired → call OpenWeatherMap API
4	Save response in DB + in-memory cache
5	Convert response to DTO
6	Send JSON response to frontend
4️⃣ Response to UI

Weather data is rendered

Weather icon displayed

Spinner stops

Errors handled gracefully

⚡ Caching Strategy (Performance Focus)
🔹 In-Memory Cache (Caffeine)

Fastest access

Max entries: 100

Expiry: 10 minutes

🔹 Database Cache (MySQL)

Stores raw API JSON

Persists across application restarts

Prevents repeated external API calls

Expiry handled using cachedAt

✅ Two-level caching ensures high performance & reliability

📡 REST API Design
🔹 Get Weather by City
GET /api/weather/{city}

Example
GET http://localhost:8080/api/weather/Pune

✅ Success Response (200 OK)
{
  "city": "Pune",
  "country": "IN",
  "temperature": 28.4,
  "feelsLike": 29.1,
  "humidity": 55,
  "weather": "Clouds",
  "description": "scattered clouds",
  "windSpeed": 3.6,
  "icon": "03d"
}

❌ Error Response (404)
{
  "timestamp": "2026-01-02T15:30:12",
  "status": 404,
  "error": "City Not Found",
  "message": "City XYZ not found",
  "path": "/api/weather/XYZ"
}

🎨 UI Highlights

Glassmorphism weather card

Animated sun & clouds

Smooth fade-in animations

Weather icons from OpenWeatherMap

Loading spinner during API calls

Clean & responsive layout

⚙️ How to Run Locally
🔧 Backend Setup
cd weather-backend


Create database:

CREATE DATABASE weatherdb;


Set environment variable:

export WEATHER_API_KEY=your_api_key_here


Run application:

mvn spring-boot:run


Backend URL:

http://localhost:8080

🎨 Frontend Setup
cd weather-ui
npm install
ng serve


Frontend URL:

http://localhost:4200

🧪 Testing

Backend: Spring Boot test framework

Frontend: Angular component tests using HttpClientTestingModule

🔒 Security Notes

API key is not committed

Uses environment variables for sensitive data

CORS configured for local frontend access

👩‍💻 Author

Sanchita Devkar
