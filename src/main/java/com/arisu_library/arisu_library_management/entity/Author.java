package com.arisu_library.arisu_library_management.entity;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_author", indexes = {
    @Index(name = "idx_author_created_date", columnList = "created_date"),
    @Index(name = "idx_author_modified_date", columnList = "modified_date"),
    @Index(name = "idx_author_name", columnList = "name"),
    @Index(name = "idx_author_dob", columnList = "dob"),
    @Index(name = "idx_author_country", columnList = "country")
})
public class Author extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    private String biography;

    private LocalDate dob;

    private String country;

    private String website;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Book> books;
}
