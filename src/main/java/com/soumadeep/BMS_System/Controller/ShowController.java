package com.soumadeep.BMS_System.Controller;

import com.soumadeep.BMS_System.DTO.ShowRequest;
import com.soumadeep.BMS_System.Entity.Show;
import com.soumadeep.BMS_System.Service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping("/add")
    public ResponseEntity<Show> addShow(@RequestBody ShowRequest request) {
        Show savedShow = showService.addShow(request);
        return ResponseEntity.ok(savedShow);
    }

    @GetMapping()
    public ResponseEntity<List<Show>> getAllShows() {
        List<Show> shows = showService.getAllShow();
        return ResponseEntity.ok(shows);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Show> getShowById(@PathVariable Long id) {
        Show show = showService.getShowById(id);
        return ResponseEntity.ok(show);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Show>> getShowByMovie(@PathVariable Long movieId) {
        List<Show> shows = showService.getShowByMovie(movieId);
        return ResponseEntity.ok(shows);
    }

    @GetMapping("/movie/{movieId}/date")
    public ResponseEntity<List<Show>>
    getShowByMovieAndDate(@PathVariable Long movieId,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        List<Show> shows = showService.getShowByMovieAndShowDate(movieId, localDate);
        return ResponseEntity.ok(shows);
    }

    @GetMapping("/screen/{screenId}/date")
    public ResponseEntity<List<Show>>
    getShowByScreenAndDate(@PathVariable Long screenId,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        List<Show> shows = showService.getShowByScreenAndShowDate(screenId, localDate);
        return ResponseEntity.ok(shows);
    }

    @GetMapping("/screen/{screenId}")
    public ResponseEntity<List<Show>> getShowsByScreen(@PathVariable Long screenId) {
        List<Show> shows = showService.getShowsByScreen(screenId);
        return ResponseEntity.ok(shows);
    }
}
