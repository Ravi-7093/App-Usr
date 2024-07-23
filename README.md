
# User Registry System

This project is a full-stack application for managing user registration, authentication, and profiles. The backend is built with Spring Boot, and the frontend is built with React using Vite.

## Table of Contents
- [Introduction](#introduction)
- [Backend Setup](#backend-setup)
  - [Packages](#packages)
  - [Entities](#entities)
  - [DTOs](#dtos)
  - [Repositories](#repositories)
  - [JWT Utils](#jwt-utils)
  - [User Details Service](#user-details-service)
  - [JWT Auth Filters](#jwt-auth-filters)
  - [Security Configuration](#security-configuration)
  - [CORS Configuration](#cors-configuration)
  - [User Management Service](#user-management-service)
  - [User Controller](#user-controller)
  - [Testing](#testing)
- [Frontend Setup](#frontend-setup)
  - [Project Creation](#project-creation)
  - [Service Methods](#service-methods)
  - [Login Page](#login-page)
  - [Registration Page](#registration-page)
  - [Footer & Navbar](#footer--navbar)
  - [Profile Page](#profile-page)
  - [Update User Page](#update-user-page)
  - [User Management Page](#user-management-page)
  - [Routing Configuration](#routing-configuration)
- [API Endpoints](#api-endpoints)
- [Running the Application](#running-the-application)

## Introduction

This application provides a comprehensive system for user management, including registration, authentication, and profile updates. It uses JWT for secure authentication and authorization.

## Backend Setup

### Packages
The backend project is structured with various packages to manage different aspects of the application:
- `com.rdev.userregistery.entity` - Contains the user entity.
- `com.rdev.userregistery.dto` - Contains data transfer objects (DTOs).
- `com.rdev.userregistery.repository` - Contains the repository interface for database operations.
- `com.rdev.userregistery.service` - Contains service classes for business logic.
- `com.rdev.userregistery.controller` - Contains REST controllers for handling HTTP requests.
- `com.rdev.userregistery.config` - Contains configuration classes for security and CORS.

### Entities
- `RegistryUsers` - Represents the user entity in the database.

### DTOs
- `ReqResp` - Data transfer object for request and response handling.

### Repositories
- `RegistryRepo` - JPA repository interface for accessing `RegistryUsers` data.

### JWT Utils
Utility class for generating and validating JWT tokens.

### User Details Service
Service for loading user-specific data during authentication.

### JWT Auth Filters
Filter for validating JWT tokens on incoming requests.

### Security Configuration
Configures HTTP security, authentication, and authorization.

### CORS Configuration
Configures Cross-Origin Resource Sharing.

### User Management Service
Service class for handling user-related operations such as registration, login, and profile updates.

### User Controller
REST controller for handling user-related HTTP requests.

### Testing
Test cases for verifying the functionality of the application.

## Frontend Setup

### Project Creation
- The frontend project is created using Vite.
- The frontend is accessible at [http://localhost:5173](http://localhost:5173).

### Service Methods
Service methods for interacting with the backend API.

### Login Page
Component for user login.

### Registration Page
Component for user registration.

### Footer & Navbar
Components for the footer and navigation bar.

### Profile Page
Component for displaying the user's profile.

### Update User Page
Component for updating user details.

### User Management Page
Component for managing users (admin only).

### Routing Configuration
Routing configuration for navigating between different pages.

## API Endpoints

### User Management Endpoints
- **Register**: `POST /auth/register`

- **Get All Users (Admin Only)**: `GET /admin/get-all-users`

- **Get User by ID (Admin Only)**: `GET /admin/get-users/{userId}`


- **Delete User (Admin Only)**: `DELETE /admin/delete/{userId}`

- **Get My Profile**: `GET /adminuser/get-profile`

## Running the Application

### Backend
1. Navigate to the backend project directory.
2. Run the application using your preferred method (e.g., `mvn spring-boot:run`).

### Frontend
1. Navigate to the frontend project directory.
2. Install dependencies: `npm install`.
3. Start the development server: `npm run dev`.
4. Access the frontend at [http://localhost:5173](http://localhost:5173).

### Testing
Run your test cases to ensure everything is working as expected.

## Notes
- Ensure you have all required environment variables set up for JWT secret, database connection, etc.

