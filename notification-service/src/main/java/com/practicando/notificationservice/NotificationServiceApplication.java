package com.practicando.notificationservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;


@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = "notificationTopic")
    public void listen(String message) {
        handleNotification(message);
    }

    public void handleNotification(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OrderPlacedEvent orderPlacedEvent = objectMapper.readValue(message, OrderPlacedEvent.class);
            log.info("Received Notification for Order - {}", orderPlacedEvent.getOrderNumber());
        } catch (Exception e) {
            log.error("Error processing notification message: {}", e.getMessage());
        }
    }

}
