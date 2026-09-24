package com.soumadeep.BMS_System.Service;

import com.soumadeep.BMS_System.DTO.ScreenRequest;
import com.soumadeep.BMS_System.Entity.Screen;
import com.soumadeep.BMS_System.Entity.Theatre;
import com.soumadeep.BMS_System.Repository.ScreenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreenService {

    private final ScreenRepository screenRepository;
    private final TheatreService theatreService;

    public Screen addScreen(ScreenRequest request){

        Theatre theatre=theatreService.getTheatreById(request.getTheatreId());
        Screen screen=Screen.builder()
                .name(request.getName())
                .totalSeats(request.getTotalSeats())
                .theatre(theatre)
                .build();

        return screenRepository.save(screen);
    }

    public List<Screen> getAllScreen(){
        return screenRepository.findAll();
    }

    public Screen getScreenById(Long id){
        return screenRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Screen not found with id:"+id));
    }

    public List<Screen> getScreenByTheatre(Long theatreId){
        return screenRepository.findByTheatreId(theatreId);
    }
}
