package com.soumadeep.BMS_System.Service;

import com.soumadeep.BMS_System.DTO.TheatreRequest;
import com.soumadeep.BMS_System.Entity.City;
import com.soumadeep.BMS_System.Entity.Theatre;
import com.soumadeep.BMS_System.Repository.TheatreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheatreService {

    private final TheatreRepository theatreRepository;
    private final CityService cityService;

    public Theatre addTheatre(TheatreRequest request){

        City city = cityService.getCityById(request.getCityId());
        Theatre theatre = Theatre.builder()
                .name(request.getName())
                .address(request.getAddress())
                .city(city)
                .build();

        return theatreRepository.save(theatre);
    }

    public List<Theatre> getAllTheatres() {
        return theatreRepository.findAll();
    }

    public Theatre getTheatreById(Long id) {
        return theatreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theatre not found with id: " + id));
    }

    public List<Theatre> getTheatresByCityId(Long cityId) {
        return theatreRepository.findByCityId(cityId);
    }
}
