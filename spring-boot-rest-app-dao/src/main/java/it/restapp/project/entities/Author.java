package it.restapp.project.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "AUTHOR")
public class Author implements Serializable {
    @Serial
    private static final long serialVersionUID = -925300265651458014L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "SURNAME")
    private String surname;
    @Column(name = "NATIONALITY")
    private String nationality;
    @Column(name = "DATE_OF_BIRTH")
    private LocalDate dateOfBirth;
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "author", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private Set<Book> books = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy="author", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<Review> reviews = new HashSet<>();
}
