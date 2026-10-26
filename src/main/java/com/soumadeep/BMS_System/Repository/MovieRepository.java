package com.soumadeep.BMS_System.Repository;

import com.soumadeep.BMS_System.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    // select * from movie where genre = ?
    List<Movie> findByGenre(String genre);

    // select * from movie where language = ?
    List<Movie> findByLanguage(String language);

    // select * from movie where title like %?%
    List<Movie> findByTitleContainingIgnoreCase(String title);

}
