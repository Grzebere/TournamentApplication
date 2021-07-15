package com.sda.tournamentApp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date;

    String name;
    String prize;
    String organizer;
    String description;
    Integer maximumNumberOfParticipants;

    @Enumerated(EnumType.STRING)
    Category category;

    @ManyToMany(mappedBy = "tournaments")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Team> teams;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "tournament")
    private Set<Phase> phases;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "tournament")
    private Set<Game> games;

// mapowania? sprawdzić

}
