package com.soumadeep.BMS_System.Service;

import com.soumadeep.BMS_System.DTO.BookingRequest;
import com.soumadeep.BMS_System.Entity.*;
import com.soumadeep.BMS_System.Enums.BookingStatus;
import com.soumadeep.BMS_System.Repository.BookingRepository;
import com.soumadeep.BMS_System.Repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final UserService userService;
    private final ShowService showService;

    @Transactional
    public Booking createBooking(BookingRequest request){

        User user=userService.getUserById(request.getUserId());
        Show show=showService.getShowById(request.getShowId());

        //Check if any of the requested seats are already booked
        List<Long> alreadyBookedSeats=bookingRepository.findBookedSeatIdsByShowId(show.getId());

        for (Long seatId:request.getSeatIds()){
            if(alreadyBookedSeats.contains(seatId)){
                throw new RuntimeException("Seat with id "+seatId+" is already booked.");
            }
        }

        List<Seat> seats=seatRepository.findAllById(request.getSeatIds());
        if(seats.size()!=request.getSeatIds().size()){
            throw new RuntimeException("Some seats Are Invalid");
        }

        double totalPrice=seats.size()*show.getTicketPrice();

        Booking booking=Booking.builder()
                .user(user)
                .show(show)
                .bookedSeats(seats)
                .totalPrice(totalPrice)
                .status(BookingStatus.CONFIRMED)
                .build();

        return bookingRepository.save(booking);
    }

    public Booking getBookingById(Long id){
        return bookingRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Booking not found with id:"+id));
    }

    public List<Booking> getBookingByUserId(Long userId){
        return bookingRepository.findByUserId(userId);
    }

    @Transactional
    public Booking cancelBooking(Long bookingId){

        Booking booking=getBookingById(bookingId);
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    public List<Seat> getAvailableSeats(Long showId){

        Show show=showService.getShowById(showId);
        List<Seat> allSeats=seatRepository.findByScreenId(show.getScreen().getId());
        List<Long> bookingSeatIds=bookingRepository.findBookedSeatIdsByShowId(showId);
        return allSeats.stream()
                .filter(seat -> !bookingSeatIds.contains(seat.getId()))
                .toList();
    }
}
