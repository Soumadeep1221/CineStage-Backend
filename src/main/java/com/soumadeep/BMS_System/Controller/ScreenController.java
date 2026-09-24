package com.soumadeep.BMS_System.Controller;

import com.soumadeep.BMS_System.DTO.ScreenRequest;
import com.soumadeep.BMS_System.Entity.Screen;
import com.soumadeep.BMS_System.Service.ScreenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
@RequiredArgsConstructor
public class ScreenController {

    private final ScreenService screenService;

    @PostMapping("/add")
    public ResponseEntity<Screen> addScreen(@RequestBody ScreenRequest request){
        Screen savedScreen=screenService.addScreen(request);
        return ResponseEntity.ok(savedScreen);
    }

    @GetMapping
    public ResponseEntity<List<Screen>> getAllScreens(){
        List<Screen> screens=screenService.getAllScreen();
        return ResponseEntity.ok(screens);
    }

    @GetMapping("/{screenId}")
    public ResponseEntity<Screen> getScreenById(@PathVariable Long screenId){
        Screen screen=screenService.getScreenById(screenId);
        return ResponseEntity.ok(screen);
    }

    @GetMapping("/theatre/{theatreId}")
    public ResponseEntity<List<Screen>> getScreensByTheaterId(@PathVariable Long theatreId){
        List<Screen> screens=screenService.getScreenByTheatre(theatreId);
        return ResponseEntity.ok(screens);
    }
}
