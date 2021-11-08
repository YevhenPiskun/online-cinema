package com.example.cinema.onlinecinema.dtos;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class MovieDto {

    @Size(min = 1, max = 200, message = "Movie's name must be between 1 and 200 characters")
    private String name;
    private Genre genre;
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Double rating;
    @Min(value = 1900, message = "Year should not be less than 1900")
    @Max(value = 2021, message = "Year should not be greater than 2021")
    private int year;
    private Tariff tariff;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }
}