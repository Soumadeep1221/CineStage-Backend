package com.soumadeep.BMS_System.Repository;

import com.soumadeep.BMS_System.Entity.Movie;
import com.soumadeep.BMS_System.Entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByGenre(String genre);
    List<Movie> findByLanguage(String language);
    List<Movie> findByTitleContainingIgnoreCase(String title);

}
