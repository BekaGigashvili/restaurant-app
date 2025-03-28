package com.javaprojects.restaurant.service.user;

public interface EmailSender {

    void send(String to, String subject, String text);
}