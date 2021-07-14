package com.sda.jz75_security_template.model;

import lombok.*;

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

    LocalDate date;
    String name;
    String prize;
    String organizer;
    String description;
    String maximumNumberOfParticipants;

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
