🌤 Weather Search Application

Demo of Project and screen shots:https://drive.google.com/drive/folders/1jRAbjCNQwXg1govmDxQLYXZ71zuvkMGv

A full-stack Weather Search Application that allows users to search for the current weather of any city.
The application fetches real-time weather data from OpenWeatherMap API, caches responses for performance, and displays rich weather information using a modern, animated UI.

📌 Features

🔍 Search current weather by city name

⚡ Fast responses using multi-level caching

🌡 Displays temperature, feels-like, humidity, wind speed, condition

🖼 Dynamic weather icons

⏳ Loading spinner and graceful error handling

🎨 Modern glassmorphism UI with animations

🧠 Clean REST API design

💾 Database-backed cache for persistence

🏗 System Architecture
Frontend (Angular)
        |
        |  HTTP Request
        v
Backend (Spring Boot REST API)
        |
        |  Fetch / Cache
        v
OpenWeatherMap API

🧩 Tech Stack
Backend
Technology	Purpose
Java 21	Core backend language
Spring Boot 3	REST API framework
Spring Data JPA	Database interaction
MySQL	Persistent cache storage
Caffeine Cache	In-memory caching
OpenWeatherMap API	Weather data provider
Frontend
Technology	Purpose
Angular (Standalone Components)	UI Framework
HTML5	Markup
CSS3	Styling & animations
TypeScript	Frontend logic
🌐 External API Used

OpenWeatherMap – Current Weather API
🔗 https://openweathermap.org/current

Sample API Call

https://api.openweathermap.org/data/2.5/weather?q=Pune&appid=YOUR_API_KEY&units=metric

🔁 Application Flow (End-to-End)
1️⃣ User Interaction

User enters a city name in the UI

Presses Enter or clicks Search

2️⃣ Frontend

Angular sends request to backend:

GET /api/weather/{city}


Shows loading spinner while waiting

3️⃣ Backend Processing

Backend follows this order:

Step	Action
1	Check Spring (Caffeine) cache
2	If not found → check MySQL DB cache
3	If cache expired → call OpenWeatherMap API
4	Save response in DB + in-memory cache
5	Convert response to DTO
6	Send JSON to frontend
4️⃣ Response to UI

UI displays weather data and icon

Spinner stops

Errors handled gracefully

⚡ Caching Strategy (Performance Focus)
🔹 In-Memory Cache (Caffeine)

Fastest access

Max entries: 100

Expiry: 10 minutes

🔹 Database Cache (MySQL)

Stores raw API JSON

Persists across restarts

Prevents repeated API calls

Expiry handled via cachedAt

This two-level caching strategy ensures high performance and reliability.

📡 REST API Design
Get Weather by City
GET /api/weather/{city}

Example
GET http://localhost:8080/api/weather/Pune

Success Response (200 OK)
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

Error Response (404)
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

Smooth fade-in effects

Weather icons from OpenWeatherMap

Loading spinner during API calls

Responsive & clean layout



⚙️ How to Run Locally
🔧 Backend Setup
cd weather-backend


Create MySQL database:

CREATE DATABASE weatherdb;


Set environment variable:

export WEATHER_API_KEY=your_api_key_here


Run application:

mvn spring-boot:run


Backend runs on:

http://localhost:8080

🎨 Frontend Setup
cd weather-ui
npm install
ng serve


Frontend runs on:

http://localhost:4200

🧪 Testing

Backend: Spring Boot test support

Frontend: Angular component tests using HttpClientTestingModule

🔒 Security Notes

API key is not committed

Uses environment variables for sensitive data

CORS configured for local frontend



👩‍💻 Author

Sanchita Devkar
