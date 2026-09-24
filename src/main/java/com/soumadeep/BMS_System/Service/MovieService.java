package com.soumadeep.BMS_System.Service;

import com.soumadeep.BMS_System.Entity.Movie;
import com.soumadeep.BMS_System.Entity.Show;
import com.soumadeep.BMS_System.Repository.BookingRepository;
import com.soumadeep.BMS_System.Repository.MovieRepository;
import com.soumadeep.BMS_System.Repository.ShowRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private  final ShowRepository showRepository;
    private final BookingRepository bookingRepository;
    private final TmdbService tmdbService;

    public Movie addMovie(Movie movie) {
        movie.setPosterUrl(tmdbService.getPosterUrl(movie.getTitle(), movie.getReleaseDate()));
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public List<Movie> getMoviesByLanguage(String language) {
        return movieRepository.findByLanguage(language);
    }

    public List<Movie> searchByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    public Movie updateMovie(Long id, Movie movieDetails) {

        Movie movie = getMovieById(id);
        movie.setTitle(movieDetails.getTitle());
        movie.setGenre(movieDetails.getGenre());
        movie.setLanguage(movieDetails.getLanguage());
        movie.setDescription(movieDetails.getDescription());
        movie.setDurationMinutes(movieDetails.getDurationMinutes());
        movie.setRating(movieDetails.getRating());
        movie.setReleaseDate(movieDetails.getReleaseDate());
        movie.setPosterUrl(tmdbService.getPosterUrl(movie.getTitle(), movie.getReleaseDate()));
        return movieRepository.save(movie);
    }

    @Transactional
    public void deleteMovie(Long movieId) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + movieId));

        List<Show> shows = showRepository.findByMovieId(movieId);

        for (Show show : shows) {

            bookingRepository.deleteByShowId(show.getId());
            showRepository.delete(show);
        }
        movieRepository.delete(movie);
    }
}
