package com.soumadeep.BMS_System.Controller;

import com.soumadeep.BMS_System.DTO.TheatreRequest;
import com.soumadeep.BMS_System.Entity.Theatre;
import com.soumadeep.BMS_System.Service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theatres")
@RequiredArgsConstructor
public class TheatreController {

    private final TheatreService theatreService;

    @PostMapping("/add")
    public ResponseEntity<Theatre> addTheatre(@RequestBody TheatreRequest request) {
        Theatre savedTheatre = theatreService.addTheatre(request);
        return ResponseEntity.ok(savedTheatre);
    }

    @GetMapping
    public  ResponseEntity<List<Theatre>> getAllTheatres() {
        List<Theatre> theatres = theatreService.getAllTheatres();
        return ResponseEntity.ok(theatres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theatre> getTheatreById(@PathVariable Long id) {
        Theatre theatre = theatreService.getTheatreById(id);
        return ResponseEntity.ok(theatre);
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<Theatre>> getTheatreByCityId(@PathVariable Long cityId) {
        List<Theatre> theatres = theatreService.getTheatresByCityId(cityId);
        return ResponseEntity.ok(theatres);
    }

}
