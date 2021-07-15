package com.sda.tournamentApp.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Phase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //ENUM uzupełnić
    @Enumerated(EnumType.STRING)
    TournamentPhase tournamentPhase;

    @ManyToOne
    Tournament tournament;

    @OneToMany(mappedBy = "phase")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<Game> games;

    //mappedby!!!
}
