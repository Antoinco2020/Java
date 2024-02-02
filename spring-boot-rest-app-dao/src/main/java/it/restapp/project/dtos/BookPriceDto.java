package it.restapp.project.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookPriceDto {
    private String description;
    private double price;
    private LocalDateTime priceStartDate;
    private LocalDateTime priceEndDate;
}
