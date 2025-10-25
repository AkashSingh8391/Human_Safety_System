# Human_Safety_System

┌────────────────────────────────────────┐
               │         SPRING BOOT BACKEND             │
               │ JWT + MySQL + WebSocket + REST APIs     │
               └──────────────────┬──────────────────────┘
                                  │
          ┌───────────────────────┼────────────────────────┐
          │                                                │
 ┌──────────────────────┐                      ┌───────────────────────┐
 │ Flutter Mobile App   │                      │ React Web Dashboard   │
 │ (Civil + Police)     │                      │ (Police + Admin)      │
 │ - SOS trigger         │                      │ - Active alerts view  │
 │ - Live location map   │                      │ - Live tracking map   │
 │ - JWT Auth            │                      │ - JWT Auth            │
 │ - Firebase optional   │                      │ - WebSocket updates   │
 └──────────────────────┘                      └───────────────────────┘

Project Title
Human Safety System with Dual Profile (Civil & Police), Real-Time SOS Alert, and Live Tracking using Spring Boot, Flutter, and MySQL.

Objective
Ek aisa integrated safety platform banana jisme civil user emergency time par apni live location ke sath alert message bhej sake aur nearby police real-time unki help ke liye location par trace kar sake.​

Technologies Used
Backend: Java Spring Boot (REST APIs, Security, JPA, MySQL)

Frontend: Flutter (cross-platform mobile app)

Database: MySQL (user, location & alert details storage)

API Integration: Google Maps API for live tracking and path tracing

Authentication: Spring Security with JWT

Notification System: Optional Firebase Cloud Messaging or local alerts

System Architecture
Actors:

Civil User (mobile app se SOS trigger karne wala)

Police Officer (alerts monitor karta hai aur live route dekhta hai)

Modules:

Dual Login Portal (Civil / Police)

SOS Alert Trigger System

Live Location & Path Tracking via Google Maps

Admin Police Dashboard

Notification & Alert Management

Database Management System (MySQL)

Data Flow:

Civil user logs in and sends SOS alert →

Spring Boot backend receives the location details →

Alert details MySQL DB me save hota hai →

Police app user ko real-time alert milta hai →

Police dashboard par live Google Maps path dikhta hai with user movement.​

Backend Overview (Spring Boot)
Key Components:

AuthController.java → JWT Login & Signup

AlertController.java → SOS submission, list & resolve APIs

UserController.java → User info operations

User.java, Alert.java → Entity models

UserRepository.java, AlertRepository.java → JPA repositories

Database config in application.properties (MySQL integration)

Main Endpoints:

POST /api/auth/login — Login (Civil/Police)

POST /api/auth/register — Register new user

POST /api/alert/sos — Send SOS alert

GET /api/alert/police — Get all active alerts

PUT /api/alert/resolve/{id} — Resolve alert

Frontend Overview (Flutter)
App Structure:

text
/lib
  main.dart
  /screens
    login_screen.dart
    civil_home_screen.dart
    police_home_screen.dart
    sos_alert_screen.dart
    live_location_screen.dart
  /services
    api_service.dart
  /models
    user.dart
    alert.dart
Core Features:

Civil dashboard → SOS Alert Button + Live Location Tracking

Police dashboard → Active SOS List + User Movement Map

Real-time tracking with moving marker & route drawn using google_maps_flutter and location package.​​

Database Schema (MySQL)
Tables:

users — user_id, username, password, role, phone_no

alerts — alert_id, user_id, message, latitude, longitude, timestamp, resolved

Additional Capabilities
Path trace using polyline points (draws user route on Google Map).​

Location auto-refresh every few seconds.

Role-based dashboards (civil/police access separation).​

Optional Firebase integration for instant police notification.​

Final Output
Civil User App: Login → Send SOS → Map + Police Tracking

Police App: Login → View Alerts → Live Map & Route Trace

Backend Admin: Database stores logs & location updates securely

