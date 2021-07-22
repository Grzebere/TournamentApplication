package com.sda.tournamentApp.repository;


import com.sda.tournamentApp.model.Team;
import com.sda.tournamentApp.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
}
