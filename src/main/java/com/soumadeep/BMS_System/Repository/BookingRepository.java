package com.soumadeep.BMS_System.Repository;

import com.soumadeep.BMS_System.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, Long> {

    //select * from booking where user_id = ?
    List<Booking> findByUserId(Long userId);

    @Query("SELECT s.id FROM Booking b JOIN b.bookedSeats s WHERE b.show.id = :showId AND b.status = 'CONFIRMED'")
    List<Long> findBookedSeatIdsByShowId(@Param("showId") Long showId);

    // delete from booking where show_id = ?
    void deleteByShowId(Long id);
}
