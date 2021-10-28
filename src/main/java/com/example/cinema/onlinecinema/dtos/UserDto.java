package com.example.cinema.onlinecinema.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class UserDto {

    @Email(message = "Email should be valid")
    private String email;
    @Size(min = 1, max = 200, message = "User's name must be between 1 and 100 characters")
    private String name;
    private Tariff tariff;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }
}