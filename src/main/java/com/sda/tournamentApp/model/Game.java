package com.sda.tournamentApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    Boolean isFinished;
    Double teamTwoPoints;
    Double teamOnePoints;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date;

    @ManyToOne
    private Tournament tournament;

    @ManyToOne
    private Team teamOne;

    @ManyToOne
    private Team teamTwo;

    @ManyToOne()
    @JsonBackReference
    private Phase phase;

// mappedBy ???

}
