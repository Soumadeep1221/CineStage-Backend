package com.soumadeep.BMS_System.DTO;

import com.soumadeep.BMS_System.Enums.SeatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatRequest {

    private String seatNumber;
    private String row;
    private Integer column;
    private Long screenId;
    private SeatType seatType;
}
