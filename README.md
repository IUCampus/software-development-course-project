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
Designing this architecture highlights the complexity of distributed systems. It shifts the developmental mindset from writing straightforward CRUD applications to managing network latency, data consistency across different databases , and orchestrating containerized deployments via Kubernetes. It also underscores the importance of observability tools (ELK, Micrometer)  because debugging across five separate services is significantly harder than debugging a monolith
