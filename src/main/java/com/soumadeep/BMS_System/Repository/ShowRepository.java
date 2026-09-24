package com.soumadeep.BMS_System.Repository;

import com.soumadeep.BMS_System.Entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

    // select * from shows where movie_id = ?1
    List<Show> findByMovieId(Long movieId);

    // select * from shows where screen_id = ?1
    List<Show> findByScreenId(Long screenId);

    // select * from shows where movie_id = ?1 and show_date = ?2
    List<Show> findByMovieIdAndShowDate(Long movieId, LocalDate showDate);

    // select * from shows where screen_id = ?1 and show_date = ?2
    List<Show> findByScreenIdAndShowDate(Long screenId, LocalDate showDate);
}
