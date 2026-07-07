package com.frankapp.backendmovie.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {

    @KafkaListener(topics = "booking-confirmed-topic", groupId = "notification-group")
    public void handleBookingConfirmed(String message) {
        // Parse the JSON message
        System.out.println("Received booking confirmation event: " + message);

        // Logic to send Email via AWS SES or SendGrid
        // emailService.sendTicket(userEmail, ticketDetails);
    }
}
