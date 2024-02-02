package it.restapp.project.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "BOOK_PRICE")
public class BookPrice implements Serializable {
    @Serial
    private static final long serialVersionUID = 6393578201881217472L;

    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PRICE")
    private double price;
    @Column(name = "START_DATE")
    private LocalDateTime priceStartDate;
    @Column(name = "END_DATE")
    private LocalDateTime priceEndDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "isbn")
    @JsonBackReference
    private Book book;
}
