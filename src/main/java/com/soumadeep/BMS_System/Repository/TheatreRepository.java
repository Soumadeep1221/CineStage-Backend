package com.soumadeep.BMS_System.Repository;

import com.soumadeep.BMS_System.Entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {

    // select * from theatres where city_id = ?1
    List<Theatre> findByCityId(Long cityId);
}
