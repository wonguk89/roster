package com.app.roster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Holiday {
    private int holidayID;
    private String holidayDate;
    private String description;
}