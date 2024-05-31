package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import application.model.Movie;
import application.model.User;
import application.repo.MovieRepository;
import application.service.MovieService;
import application.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> addMovieToWatchlist(@RequestBody Movie movie, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        movie.setUser(user); 
        movieService.save(movie);

        return ResponseEntity.ok("Movie added to watchlist");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeMovieFromWatchlist(@PathVariable Long id, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        Movie movie = movieService.findByIdAndUser(id, user);
        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found in watchlist");
        }

        movieService.delete(movie);

        return ResponseEntity.ok("Movie removed from watchlist");
    }
    @GetMapping("/favorites")
    public ResponseEntity<?> getFavoriteMovies(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        List<Movie> favoriteMovies = movieRepository.findAllByUser(user);
        favoriteMovies.forEach(movie -> System.out.println("Fetched movie: " + movie.getTitle()));

        return ResponseEntity.ok(favoriteMovies);
    }


}
