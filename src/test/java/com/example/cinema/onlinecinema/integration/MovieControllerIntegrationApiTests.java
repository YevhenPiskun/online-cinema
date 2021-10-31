package com.example.cinema.onlinecinema.integration;

import com.example.cinema.onlinecinema.core.AbstractApiTest;
import com.example.cinema.onlinecinema.core.DataFactory;
import com.example.cinema.onlinecinema.dtos.MovieDto;
import com.example.cinema.onlinecinema.dtos.Tariff;
import com.example.cinema.onlinecinema.exceptions.MovieNotFoundException;
import com.example.cinema.onlinecinema.repositories.models.Movie;
import com.example.cinema.onlinecinema.services.MovieService;
import com.example.cinema.onlinecinema.utils.FieldErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MovieControllerIntegrationApiTests extends AbstractApiTest {

    @MockBean
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        Mockito.reset(movieService);
    }

    @Test
    void whenUserGetAllMovies_thenAllMoviesIsReturned() throws Exception {

        List<MovieDto> movieDtos = DataFactory.createMovieDtoList();

        Mockito.when(movieService.getAllMovies())
                .thenReturn(movieDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/movie/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(movieDtos)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void whenUserGetMovieByName_thenMoviesIsReturned() throws Exception {

        MovieDto movieDto = DataFactory.createMovieDto();

        Mockito.when(movieService.getMovieByName(ArgumentMatchers.anyString()))
                .thenReturn(movieDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/movie")
                .param("name", "Titanic"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(movieDto)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void whenUserAddNewMovie_thenMovieIsCreated() throws Exception {
        Movie movie = DataFactory.createMovie();
        MovieDto movieDto = DataFactory.createMovieDto();

        Mockito.when(movieService.addMovie(ArgumentMatchers.any(MovieDto.class)))
                .thenReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/movie")
                .content(asJsonString(movieDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void whenUserUpdateMovie_thenMovieIsSuccessfullyUpdated() throws Exception {
        Movie movie = DataFactory.createMovie();
        movie.setTariff(Tariff.MEDIUM);
        MovieDto movieDto = DataFactory.createMovieDto();
        movieDto.setTariff(Tariff.MEDIUM);

        Mockito.when(movieService.updateMovie(ArgumentMatchers.any(MovieDto.class)))
                .thenReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/movie")
                .content(asJsonString(movieDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void whenUserUpdateNotExistingMovie_thenMovieNotFoundExceptionIsExpected() throws Exception {

        MovieDto movieDto = DataFactory.createMovieDto();

        Mockito.when(movieService.updateMovie(ArgumentMatchers.any(MovieDto.class)))
                .thenThrow(MovieNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/movie")
                .content(asJsonString(movieDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void whenUserAddMovieWithWrongYear_thenValidationErrorIsExpected() throws Exception {

        MovieDto movieDto = DataFactory.createMovieDto();
        movieDto.setYear(2022);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/movie")
                .content(asJsonString(movieDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<FieldErrorMessage> listErrorMessages = new ArrayList<>();
        listErrorMessages.add(new FieldErrorMessage("year", "Year should not be greater than 2021"));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = asJsonString(listErrorMessages);

        assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
    }

    @Test
    void whenUserAddMovieWithWrongRating_thenValidationErrorIsExpected() throws Exception {

        MovieDto movieDto = DataFactory.createMovieDto();
        movieDto.setRating(10.1);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/movie")
                .content(asJsonString(movieDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<FieldErrorMessage> listErrorMessages = new ArrayList<>();
        listErrorMessages.add(new FieldErrorMessage("rating", "must be less than or equal to 10.0"));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = asJsonString(listErrorMessages);

        assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}