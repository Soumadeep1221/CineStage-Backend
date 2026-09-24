package com.soumadeep.BMS_System.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenRequest {

    private String name;
    private Long theatreId;
    private Integer totalSeats;
}
