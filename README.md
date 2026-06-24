# software-development-course-project
### Goal & Situation:
Many movie theaters struggle with outdated systems that fail during peak traffic, lack real-time seat availability, and provide poor user experiences. The goal of this application is to solve these issues by building a scalable, microservices-based Movie Booking System that enables smooth reservations, real-time seat selection, and secure payments.  Application Components:
The application is divided into a frontend single-page application (SPA) and a backend microservices cluster.  Frontend Web Application: Developed using React and Material UI (MUI). It serves as the primary touchpoint for users to browse movies, select seats, and manage bookings.API Gateway (Spring Cloud Gateway): Acts as the single entry point for the frontend to communicate with the backend services. It handles routing, CORS, and preliminary authentication checks.  User Service: Manages authentication (JWT), user profiles, and roles (admin/customer).  Movie Service: Manages the catalog of movies, showtimes, ratings, genres, and descriptions.  Booking Service: The core transactional service handling real-time seat locking, ticket generation, and reservation confirmations.  Payment Service: Integrates with third-party gateways (e.g., Stripe) to process secure transactions.  Review & Rating Service: Collects post-movie user feedback.  Message Broker (Apache Kafka): Facilitates asynchronous, event-driven communication between services (e.g., the Booking Service emitting a BookingConfirmed event that triggers the Notification Service via Kafka).  Wireframe Concept (Textual Description):View 1: Dashboard/Home: A Material UI grid displaying movie posters (cards) with titles, genres, and "Book Now" buttons.View 2: Movie Details & Showtime: A detailed view showing the synopsis and a date/time selector for available shows.View 3: Seat Selection: An interactive grid of seats (colored green for available, gray for booked, yellow for selected).View 4: Checkout: A summary of the booking with a secure payment form.

### Software Architecture (C4 Model - Container Level)
To satisfy the architecture design requirement, here is a textual representation of the C4 Container Model:[Container] Web Application (React + Material UI): Delivers the UI to the user's browser.Communicates with: API Gateway via HTTPS/REST.[Container] API Gateway (Spring Cloud Gateway): Routes incoming frontend requests to the appropriate microservice.  [Container] Microservices Cluster (Spring Boot Java): Each service is independently deployed.  User Service -> Reads/Writes to User DB (PostgreSQL).  Movie Service -> Reads/Writes to Movie DB (PostgreSQL).  Booking Service -> Reads/Writes to Booking DB (PostgreSQL). Publishes to Kafka.  Payment Service -> Communicates with external Stripe API. Publishes to Kafka.  [Container] Message Broker (Apache Kafka): Handles async events.  [Container] Observability Stack: ELK (Elasticsearch, Logstash, Kibana) for centralized logging and Micrometer for telemetry.  

###  Implementation & Technology StackBackend Technologies:
The backend utilizes Java with Spring Boot to package services independently. Data is stored in relational SQL databases (PostgreSQL/MySQL via AWS RDS). The entire infrastructure is containerized using Docker and orchestrated via Kubernetes on AWS EKS.  Frontend Implementation (React + Material UI):The frontend will consume the REST APIs exposed by the Spring Cloud Gateway. Below is a conceptual implementation of how the Movie Service and Booking Service data would be rendered using React and Material UI.

import React, { useState, useEffect } from 'react';
import { Card, CardContent, CardMedia, Typography, Button, Grid, Container } from '@mui/material';
import axios from 'axios';

## Conceptual React Component for displaying movies
<img width="1028" height="838" alt="image" src="https://github.com/user-attachments/assets/55f84d3e-60ca-48e5-a039-ff184e0a7ea5" />

### Testing & Documentation Strategy
To meet the application requirements from your task brief:

Unit Testing:

Backend: Use JUnit 5 and Mockito to test isolated business logic in Spring Boot (e.g., verifying that the Booking Service correctly calculates the total price).

Frontend: Use Jest and React Testing Library to ensure Material UI components render correctly and state changes (like selecting a seat) work as expected.

Integration Testing: Use Testcontainers in Java to spin up temporary Docker instances of PostgreSQL and Kafka. This ensures the Booking Service interacts correctly with the actual database and message queue during automated tests.

Code Documentation:

API Level: Integrate Swagger/OpenAPI via springdoc-openapi to automatically generate interactive API documentation for all microservices.

Code Level: Use standard JavaDoc for backend logic and JSDoc for complex React hooks and utilities.

### Critical EvaluationHas the goal been achieved?
Yes. By decentralizing the monolithic structure into independent microservices (User, Movie, Booking, Payment) , the system can now scale specific components (like the Booking service) during peak times (e.g., a major movie release) without crashing the entire platform. The use of Kafka ensures that processes like email notifications don't block the user's booking flow.  Improvements for the future:Caching: Implement Redis for caching frequently accessed data like the daily movie catalog or available showtimes to reduce the load on the SQL database.Saga Pattern: For distributed transactions (e.g., ensuring a seat is released if the Payment Service fails), implementing the Saga pattern with compensating transactions would make the system more resilient.Frontend State Management: As the React app grows, introducing Redux Toolkit or Zustand will help manage complex global states (like the user's cart and authentication tokens) more cleanly than standard React Context.Accumulated experience and personal development:
Designing this architecture highlights the complexity of distributed systems. It shifts the developmental mindset from writing straightforward CRUD applications to managing network latency, data consistency across different databases , and orchestrating containerized deployments via Kubernetes. It also underscores the importance of observability tools (ELK, Micrometer)  because debugging across five separate services is significantly harder than debugging a monolith.

### API Gateway (Spring Cloud Gateway)
This service acts as the single entry point for your React frontend, routing requests to the appropriate underlying microservices.  
## application.yml
<img width="983" height="640" alt="image" src="https://github.com/user-attachments/assets/cfc0b5ea-4521-4b39-ae5c-362087aead35" />

### 2. Movie Service
This service manages the movie catalog and showtimes. It connects to its own isolated PostgreSQL database.  
## Movie.java (Entity)
<img width="828" height="456" alt="image" src="https://github.com/user-attachments/assets/0a06b1b6-c168-400d-aa47-8c978f4f1b87" />
## MovieController.java (REST API)
<img width="931" height="713" alt="image" src="https://github.com/user-attachments/assets/2ebf255b-3810-4fc4-9a32-cd21d6f49f87" />

### 3. Booking Service (Core Logic)
This is the most complex service. It handles seat selection, saves the booking to the database , and publishes an event to Apache Kafka so the Notification Service or Payment Service can process it asynchronously. 
## Booking.java (Entity)  
<img width="792" height="475" alt="image" src="https://github.com/user-attachments/assets/1e5b69ec-ff36-4225-b1d7-e17a73294763" />


C4Container
    title C4 Container Model: Movie Booking Microservices Architecture

    Person(user, "User / Admin", "Browses movies, selects seats, and books tickets. Admins manage the catalog.")

    System_Boundary(movie_booking_system, "Movie Booking Platform") {
        Container(frontend, "Frontend SPA", "React, Material UI", "Provides the user interface for browsing and booking.")
        Container(api_gateway, "API Gateway", "Spring Cloud Gateway", "Single entry point; routes requests to the appropriate backend microservice.")
        
        Container(user_service, "User Service", "Spring Boot", "Handles user authentication, profile management, and access roles.")
        ContainerDb(user_db, "User DB", "RDB SQL (AWS RDS)", "Stores user accounts and roles.")
        
        Container(movie_service, "Movie Service", "Spring Boot", "Manages movie details, genres, ratings, and showtimes.")
        ContainerDb(movie_db, "Movie DB", "RDB SQL (AWS RDS)", "Stores the movie catalog and schedules.")
        
        Container(booking_service, "Booking Service", "Spring Boot", "Handles real-time seat selection, ticket booking, and reservations.")
        ContainerDb(booking_db, "Booking DB", "RDB SQL (AWS RDS)", "Stores reservations and seat locks.")
        
        Container(payment_service, "Payment Service", "Spring Boot", "Processes payment transactions.")
        
        Container(review_service, "Review & Rating Service", "Spring Boot", "Allows users to rate and review movies after watching.")
        ContainerDb(review_db, "Review DB", "RDB SQL (AWS RDS)", "Stores post-movie user feedback.")

        ContainerQueue(kafka, "Message Broker", "Apache Kafka", "Event-driven communication for async tasks (e.g., notifications).")
    }

    System_Ext(payment_gateway, "Third-Party Payment Gateway", "Stripe, PayPal", "External service processing financial transactions.")
    System_Ext(notification_service, "External Notifications", "Email/SMS", "Sends booking confirmations and promotions.")

    %% User to Frontend to Gateway
    Rel(user, frontend, "Interacts with UI")
    Rel(frontend, api_gateway, "Makes REST API requests to")

    %% Gateway to Microservices
    Rel(api_gateway, user_service, "Routes /users traffic to")
    Rel(api_gateway, movie_service, "Routes /movies traffic to")
    Rel(api_gateway, booking_service, "Routes /bookings traffic to")
    Rel(api_gateway, payment_service, "Routes /payments traffic to")
    Rel(api_gateway, review_service, "Routes /reviews traffic to")

    %% Microservices to Databases (RDB SQL)
    Rel(user_service, user_db, "Reads/Writes user data")
    Rel(movie_service, movie_db, "Reads/Writes movie data")
    Rel(booking_service, booking_db, "Reads/Writes booking data")
    Rel(review_service, review_db, "Reads/Writes review data")

    %% Kafka Interactions
    Rel(booking_service, kafka, "Publishes booking events to")
    Rel(kafka, notification_service, "Triggers notifications after booking")

    %% External Interactions
    Rel(payment_service, payment_gateway, "Integrates with to process payments")




