package com.example.cinema.onlinecinema.dtos;

public enum Tariff {

    BASE(3),
    MEDIUM(2),
    PREMIUM(1);

    Tariff(int priority) {
        this.priority = priority;
    }

    private int priority;
}