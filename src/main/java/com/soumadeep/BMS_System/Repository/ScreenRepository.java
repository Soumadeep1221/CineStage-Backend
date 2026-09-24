package com.soumadeep.BMS_System.Repository;

import com.soumadeep.BMS_System.Entity.Screen;
import com.soumadeep.BMS_System.Entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreenRepository extends JpaRepository<Screen, Long> {

    // select * from screens where theatre_id = ?1
    List<Screen> findByTheatreId(Long theatreId);

}
