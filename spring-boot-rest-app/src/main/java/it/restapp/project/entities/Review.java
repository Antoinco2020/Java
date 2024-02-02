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
@Table(name = "REVIEW")
public class Review implements Serializable {
    @Serial
    private static final long serialVersionUID = 377548993284800092L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "DATE")
    private LocalDateTime date;
    @Column(name = "VOTE")
    private int vote;
    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorId", referencedColumnName = "id")
    @JsonBackReference
    private Author author;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "isbn")
    @JsonBackReference
    private Book book;
}
