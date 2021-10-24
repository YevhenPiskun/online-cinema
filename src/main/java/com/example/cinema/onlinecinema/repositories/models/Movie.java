package com.example.cinema.onlinecinema.repositories.models;

import com.example.cinema.onlinecinema.dtos.Genre;
import com.example.cinema.onlinecinema.dtos.Tariff;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity(name="movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    @Size(min = 1, max = 200, message = "Movie's name must be between 1 and 200 characters")
    private String name;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Double rating;
    @Min(value = 1900, message = "Year should not be less than 1900")
    @Max(value = 2021, message = "Year should not be greater than 2021")
    private int year;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tariff tariff;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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