package it.restapp.project.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "BOOK_DATA")
public class BookData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ISBN")
    private String isbn;
    @Column(name = "BOOK_CLASSIFICATION")
    private String bookClassification;
    @Column(name = "PUBLISHER")
    private String publisher;
    @Column(name = "PUBLICATION_DATE")
    private LocalDate publicationDate;
    @Column(name = "TOTAL_SOLD")
    private int totalSold;
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @JsonBackReference
    private Book book;
}
