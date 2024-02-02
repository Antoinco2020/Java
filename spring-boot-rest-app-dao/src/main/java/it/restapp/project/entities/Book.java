package it.restapp.project.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "BOOK")
public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 544655282608675596L;
    @Id
    @Column(name= "ISBN", unique = true)
    private String isbn;
    @Column(name= "TITLE")
    private String title;
    @Column(name= "TOTAL_PAGE")
    private int totalPage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @JsonBackReference
    private Author author;
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private BookData bookData;
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy="book", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<BookPrice> bookPrices = new HashSet<>();
    @OneToMany(mappedBy="book")
    @EqualsAndHashCode.Exclude
    private Set<Review> reviews = new HashSet<>();
}
