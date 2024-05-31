package application.service;

import java.util.List;

import application.model.Movie;
import application.model.User;

public interface MovieService {
    Movie saveMovie(Movie movie);
    void save(Movie movie);
    void delete(Movie movie);
    List<Movie> getAllMovies();
    Movie findByIdAndUser(Long id, User user);
}
