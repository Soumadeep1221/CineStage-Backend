package com.soumadeep.BMS_System.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TmdbService {

    @Value("${tmdb.api-key}")
    private String apiKey;

    private final RestClient restClient = RestClient.builder()
            .baseUrl("https://api.themoviedb.org/3")
            .requestFactory(
                    new HttpComponentsClientHttpRequestFactory()
            )
            .build();

    public String getPosterUrl(String title, LocalDate releaseDate) {

        JsonNode response = restClient.get()
                .uri(uriBuilder -> {
                    uriBuilder
                            .path("/search/movie")
                            .queryParam("api_key", apiKey)
                            .queryParam("query", title);

                    if (releaseDate != null) {
                        uriBuilder.queryParam(
                                "year",
                                releaseDate.getYear()
                        );
                    }

                    return uriBuilder.build();
                })
                .retrieve()
                .body(JsonNode.class);

        if (response == null || !response.has("results")) {
            throw new RuntimeException(
                    "Could not search TMDB for movie: " + title
            );
        }

        JsonNode results = response.get("results");

        if (!results.isArray() || results.isEmpty()) {
            throw new RuntimeException(
                    "Movie not found on TMDB: " + title
            );
        }

        JsonNode selectedMovie = null;

        for (JsonNode movie : results) {

            String tmdbTitle =
                    movie.path("title").asText();

            if (tmdbTitle.equalsIgnoreCase(title)) {
                selectedMovie = movie;
                break;
            }
        }

        if (selectedMovie == null) {
            selectedMovie = results.get(0);
        }

        String posterPath =
                selectedMovie.path("poster_path").asText(null);

        if (posterPath == null || posterPath.isBlank()) {
            throw new RuntimeException(
                    "No poster found on TMDB for movie: " + title
            );
        }

        return "https://image.tmdb.org/t/p/w500" + posterPath;
    }
}