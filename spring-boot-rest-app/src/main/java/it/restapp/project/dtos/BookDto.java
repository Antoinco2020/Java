package it.restapp.project.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BookDto {
    private String isbn;
    private String title;
    private String publisher;
    private int totalPage;
    private String bookClassification;
    private LocalDate publicationDate;
    private int totalSold;
    private List<BookPriceDto> prices = new ArrayList<>();
    private List<ReviewDto> reviews = new ArrayList<>();
}
