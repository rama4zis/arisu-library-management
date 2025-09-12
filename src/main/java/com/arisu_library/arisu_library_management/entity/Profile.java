package com.arisu_library.arisu_library_management.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "m_profile", indexes = {
        @Index(name = "idx_profile_created_date", columnList = "created_date"),
        @Index(name = "idx_profile_modified_date", columnList = "modified_date"),
        @Index(name = "idx_profile_first_name", columnList = "first_name"),
        @Index(name = "idx_profile_last_name", columnList = "last_name"),
        @Index(name = "idx_profile_dob", columnList = "dob"),
        @Index(name = "idx_profile_city", columnList = "city"),
        @Index(name = "idx_profile_phone", columnList = "phone")
})
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String phone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}