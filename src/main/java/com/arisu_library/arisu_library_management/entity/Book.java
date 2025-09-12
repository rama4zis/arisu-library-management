package com.arisu_library.arisu_library_management.entity;
import java.time.LocalDate;

import com.arisu_library.arisu_library_management.model.enums.BookStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "m_book", indexes = {
    @Index(name = "idx_book_created_date", columnList = "created_date"),
    @Index(name = "idx_book_modified_date", columnList = "modified_date"),
    @Index(name = "idx_book_title", columnList = "title"),
    @Index(name = "idx_book_author_id", columnList = "author_id"),
    @Index(name = "idx_book_category_id", columnList = "category_id"),
    @Index(name = "idx_book_status", columnList = "status")
})
public class Book extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String title;

    private String summary;

    private String coverImage;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private Integer pages;

    private String language;

    private LocalDate publishedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatus status;

}
