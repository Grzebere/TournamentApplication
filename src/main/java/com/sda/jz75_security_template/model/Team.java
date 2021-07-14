package com.sda.jz75_security_template.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany()
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Tournament> tournaments;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "teamOne")
    private Set<Game> gamesAsTeamOne;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "teamTwo")
    private Set<Game> gamesAsTeamTwo;

    @ManyToMany()
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Account> users;

}
